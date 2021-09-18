package dao.impl;

import com.google.zxing.WriterException;
import dao.PaperDao;
import domain.Paper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import util.QRUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class PaperDaoImpl implements PaperDao
{
    private final JdbcTemplate template = new JdbcTemplate((JDBCUtils.getDataSource()));

    @Override
    public int findTotalCount(String condition)
    {
        // If user is NOT searching papers with conditions
        if (condition == null || "".equals(condition))
        {
            String sql = "SELECT COUNT(*) FROM `paper` where `is_published` = ?";
            return template.queryForObject(sql, Integer.class, 1);
        }
        else
        { // If user is searching papers with conditions
            String sqlCondition = "SELECT COUNT(*) FROM `paper` WHERE (`title` like ? or `author` like ? or " +
                    "`keyword` like ?) and `is_published` = ?;";
            // Fuzzy search
            String likeCondition = "%" + condition + "%";
            return template.queryForObject(sqlCondition, Integer.class, likeCondition,
                    likeCondition, likeCondition, 1);
        }
    }

    @Override
    public List<Paper> findByPage(int start, int rows, String condition)
    {
        // If user is NOT searching papers with conditions
        if (condition == null || "".equals(condition))
        {
            String sql = "SELECT * FROM `paper` where is_published = ? LIMIT ?, ?;";
            return template.query(sql, new BeanPropertyRowMapper<>(Paper.class), 1, start, rows);
        }
        else
        { // If user is searching papers with conditions
            String sqlCondition = "SELECT * FROM `paper` WHERE (`title` like ? or `author` like ? or `keyword`" +
                    " like ?) and is_published = ? limit ?, ?;";
            // Fuzzy search
            String likeCondition = "%" + condition + "%";
            return template.query(sqlCondition, new BeanPropertyRowMapper<>(Paper.class), likeCondition,
                    likeCondition, likeCondition, 1, start, rows);
        }
    }

    @Override
    public boolean checkPaperMajor(String major) {
        return new ReviewerDaoImpl().checkReviewerMajor(major);
    }

    @Override
    public int addPaper(Paper paper)
    {
        // Search in database to find the reviewers in the same major
        final String findReviewers = "SELECT `id` FROM `reviewer` WHERE `major` = ? ORDER BY `count_task`;";
        List<Map<String, Object>> reviewersMaps = template.queryForList(findReviewers, paper.getMajor());
        // If the number of majors is less than 3, return 0
        if (reviewersMaps.size() < 3)
            return 0;

        // Set the reviewers' IDs
        paper.setRev_id_1(Integer.parseInt(reviewersMaps.get(0).get("id").toString()));
        paper.setRev_id_2(Integer.parseInt(reviewersMaps.get(1).get("id").toString()));
        paper.setRev_id_3(Integer.parseInt(reviewersMaps.get(2).get("id").toString()));
        // Insert new paper information into the table
        final String insertPaper = "INSERT INTO `paper` " +
                "(title, author, university, outline, keyword, major, rev_id_1, rev_id_2, rev_id_3, submit_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        template.update(insertPaper, paper.getTitle(), paper.getAuthor(), paper.getUniversity(),
                paper.getOutline(), paper.getKeyword(), paper.getMajor(), paper.getRev_id_1(),
                paper.getRev_id_2(), paper.getRev_id_3(), paper.getSubmit_date());
        // Update the reviewer information of the amount tasks
        final String addCountTask = "UPDATE `reviewer` SET `count_task` = `count_task` + 1 WHERE `id` IN (?, ?, ?);";
        template.update(addCountTask, paper.getRev_id_1(), paper.getRev_id_2(), paper.getRev_id_3());
        return 1;
    }

    @Override
    public List<Paper> findMyPapers(Object email)
    {
        // Query university name for given email
        String queryName = "select name from university where email = ?";
        Map<String, Object> universityName = template.queryForMap(queryName, email);
        // Query papers for given university
        String sql = "select * from paper where university = ?;";
        return template.query(sql, new BeanPropertyRowMapper<>(Paper.class), universityName.get("name"));
    }

    @Override
    public void delete(int id)
    {
        // Delete paper with given id
        String sql = "delete from paper where id = ?";
        template.update(sql, id);
    }

    @Override
    public int findLastId()
    {
        // Find the id of the last record
        // Query total number of papers in database
        String queryCount = "select count(*) from paper";
        int paperCount = template.queryForObject(queryCount, Integer.class);
        if (paperCount == 0)
        { // If paper table is empty
            return 0;
        }
        else
        {
            String sql = "select id from paper order by id desc limit 1";
            Map<String, Object> map = template.queryForMap(sql);
            Number id = (Number) map.get("id");
            return id.intValue();
        }
    }

    @Override
    public List<Paper> findReviewPapers(Object email)
    {
        // Query reviewer id for given email
        String queryId = "select id from reviewer where email = ?";
        Map<String, Object> idMap = template.queryForMap(queryId, email);
        Number reviewer_id = (Number) idMap.get("id");
        int reviewerId = reviewer_id.intValue();
        // Query papers with given reviewer id
        String sql = "select * from paper where ((rev_id_1 = ? and acceptance_1 = ?) or (rev_id_2 = ? and " +
                "acceptance_2 = ?) or (rev_id_3 = ? and acceptance_3 = ?)) and is_published = ?";
        return template.query(sql, new BeanPropertyRowMapper<>(Paper.class), reviewerId, 0, reviewerId, 0,
                reviewerId, 0, 0);
    }

    @Override
    public void reviewPaper(String comment, int isAccept, int paperId, String reviewerEmail, String realpath)
    {
        // Query reviewer id for given reviewer email
        String queryReviewerId = "select id from reviewer where email = ?";
        Map<String, Object> reviewerIdMap = template.queryForMap(queryReviewerId, reviewerEmail);
        Number reviewer_id = (Number)reviewerIdMap.get("id");
        int reviewerId = reviewer_id.intValue();
        // Reduce the count of task of this reviewer
        String countTaskQuery = "select count_task from reviewer where id = ?";
        Map<String, Object> countTaskMap = template.queryForMap(countTaskQuery, reviewerId);
        Number count_task = (Number) countTaskMap.get("count_task");
        int countTask = count_task.intValue();
        String reduceCountQuery = "update reviewer set count_task = ? where id = ?";
        template.update(reduceCountQuery, countTask - 1, reviewerId);
        // Query three reviewer id for given paper id
        String queryPaperReviewer = "select rev_id_1, rev_id_2, rev_id_3 from paper where id = ?";
        Map<String, Object> paperReviewerMap = template.queryForMap(queryPaperReviewer, paperId);
        Number reviewer_1 = (Number) paperReviewerMap.get("rev_id_1");
        Number reviewer_2 = (Number) paperReviewerMap.get("rev_id_2");
        Number reviewer_3 = (Number) paperReviewerMap.get("rev_id_3");
        int reviewer1 = reviewer_1.intValue();
        int reviewer2 = reviewer_2.intValue();
        int reviewer3 = reviewer_3.intValue();
        // Update the paper being reviewed in the database
        if (reviewerId == reviewer1)
        {
            String updateSql = "update paper set comment_1 = ?, acceptance_1 = ? where id = ?";
            template.update(updateSql, comment, isAccept, paperId);
        }
        else if (reviewerId == reviewer2)
        {
            String updateSql = "update paper set comment_2 = ?, acceptance_2 = ? where id = ?";
            template.update(updateSql, comment, isAccept, paperId);
        }
        else if (reviewerId == reviewer3)
        {
            String updateSql = "update paper set comment_3 = ?, acceptance_3 = ? where id = ?";
            template.update(updateSql, comment, isAccept, paperId);
        }
        // Check whether paper is allowed to publish
        String checkPaper = "select acceptance_1, acceptance_2, acceptance_3 from paper where id = ?";
        Map<String, Object> checkPaperMap = template.queryForMap(checkPaper, paperId);
        Number acceptance_1 = (Number) checkPaperMap.get("acceptance_1");
        Number acceptance_2 = (Number) checkPaperMap.get("acceptance_2");
        Number acceptance_3 = (Number) checkPaperMap.get("acceptance_3");
        int acceptance1 = acceptance_1.intValue();
        int acceptance2 = acceptance_2.intValue();
        int acceptance3 = acceptance_3.intValue();
        // If at least two reviewers accept the paper, make the paper published
        if ((acceptance1 == 1 && acceptance2 == 1) || (acceptance1 == 1 && acceptance3 == 1) ||
                (acceptance2 == 1 && acceptance3 == 1))
        {
            String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
            String acceptSql = "update paper set is_published = ?, publish_date = ? where id = ?";
            template.update(acceptSql, 1, date, paperId);
            // Add digital signature on paper
            // First query the paper needed to be added with digital signature
            String sql = "select * from paper where id = ?";
            List<Paper> paperList = template.query(sql, new BeanPropertyRowMapper<>(Paper.class), paperId);
            // Set parameters
            File pdf = new File(realpath + "/papers/" + paperId + ".pdf");
            File img = new File(realpath + "/QRCode/" + paperId + ".png");
            try
            {
                // Call function and add digital signature
                QRUtils.AddInfoToPDF(paperList.get(0), pdf, img);
            }
            catch (WriterException | IOException e) { e.printStackTrace(); }
        }
    }
}
