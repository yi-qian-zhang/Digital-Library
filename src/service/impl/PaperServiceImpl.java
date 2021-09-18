package service.impl;

import dao.PaperDao;
import dao.impl.PaperDaoImpl;
import domain.PageBean;
import domain.Paper;
import service.PaperService;
import java.util.List;

public class PaperServiceImpl implements PaperService
{
    private final PaperDao paperDao = new PaperDaoImpl();

    @Override
    public PageBean<Paper> findPaperByPage(String _currentPage, String _rows, String condition)
    {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        // Handle situation where user clicks "previous arrow" while in first page
        if (currentPage <= 0)
            currentPage = 1;

        PageBean<Paper> pb = new PageBean<>();
        // Set total numbers of papers each page
        pb.setRows(rows);
        // Query total amount of papers
        int totalCount = paperDao.findTotalCount(condition);
        pb.setTotalCount(totalCount);
        // Calculate numbers of pages
        int totalPage = totalCount % rows == 0 ? totalCount / rows : (totalCount / rows) + 1;
        pb.setTotalPage(totalPage);
        // Handle situations where user clicks "next arrow" while in last page
        if (currentPage >= totalPage)
            currentPage = totalPage;

        // Calculate the index of the head record for each page
        int start = 0;
        if (totalCount != 0)
            start = (currentPage - 1) * rows;

        // Query list of papers for given page
        List<Paper> paperList = paperDao.findByPage(start, rows, condition);
        pb.setList(paperList);
        // Set current page
        pb.setCurrentPage(currentPage);
        return pb;
    }

    @Override
    public boolean checkPaperMajor(String major) {
        return paperDao.checkPaperMajor(major);
    }

    @Override
    public int addPaper(Paper paper) {
        return paperDao.addPaper(paper);
    }

    @Override
    public List<Paper> findMyPapers(Object email) {
        return paperDao.findMyPapers(email);
    }

    @Override
    public void deletePaper(String id) {
        paperDao.delete(Integer.parseInt(id));
    }

    @Override
    public int findLastId() {
        return paperDao.findLastId();
    }

    @Override
    public List<Paper> findReviewPapers(Object email) {
        return paperDao.findReviewPapers(email);
    }

    @Override
    public void reviewPaper(String comment, String is_accept, String paper_id, String reviewerEmail, String realPath)
    {
        // Transform variables corresponding to the field in database
        int isAccept = (is_accept.equals("true") ? 1 : -1);
        int paperId = Integer.parseInt(paper_id);
        // Update paper being reviewed in the database
        paperDao.reviewPaper(comment, isAccept, paperId, reviewerEmail, realPath);
    }
}
