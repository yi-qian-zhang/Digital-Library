package dao.impl;

import dao.ReviewerDao;
import domain.Reviewer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import java.util.Map;

public class ReviewerDaoImpl implements ReviewerDao
{
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Reviewer findReviewerByEmailAndPassword(String email, String password)
    {
        try
        {
            String sql = "SELECT * FROM `reviewer` WHERE `email` = ? AND `password` = ?;";
            // If there is matching reviewer in the database, return it
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(Reviewer.class), email, password);
        }
        catch (Exception e)
        {
            // It will print the error if no matched result found, it should not be printed (by Li, Qi)
            // e.printStackTrace();
            // If there's no matching reviewer in the database, return null
            return null;
        }
    }

    @Override
    public boolean checkReviewerEmail(String email)
    {
        try
        {
            String sql = "SELECT `id` FROM `reviewer` WHERE `email` = ?;";
            return template.queryForList(sql, email).isEmpty();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean checkReviewerMajor(String major)
    {
        try
        {
            String sql = "SELECT `id` FROM `major` WHERE `name` = ?;";
            return !template.queryForList(sql, major).isEmpty();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addReviewer(Reviewer reviewer)
    {
        try
        {
            // Query if registered reviewer already exists in database
            String querySql = "SELECT `email` FROM `reviewer` WHERE `email` = ?;";
            Map<String, Object> result = template.queryForMap(querySql, reviewer.getEmail());
            // If the registered reviewer already exists in database, return true
            if (result.get("email") != null)
                return true;
        }
        catch (DataAccessException e)
        {
            String sql = "INSERT INTO `reviewer` (`email`, `password`, `name`, `major`) VALUES (?, ?, ?, ?)";
            // Add new registered reviewer into database
            template.update(sql, reviewer.getEmail(), reviewer.getPassword(), reviewer.getName(), reviewer.getMajor());
            return false;
        }
        return false;
    }
}
