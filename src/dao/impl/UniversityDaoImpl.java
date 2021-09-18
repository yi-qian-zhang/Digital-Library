package dao.impl;

import dao.UniversityDao;
import domain.University;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import java.util.Map;

public class UniversityDaoImpl implements UniversityDao
{
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public University findUniversityByEmailAndPassword(String email, String password)
    {
        try
        {
            String sql = "SELECT * FROM `university` WHERE `email` = ? AND `password` = ?;";
            // If there is matching university in the database, return it
            return template.queryForObject(sql, new BeanPropertyRowMapper<>(University.class), email, password);
        }
        catch (Exception e)
        {
            // It will print the error if no matched result found, it should not be printed (by Li, Qi)
            // e.printStackTrace();
            // If there's no matching university in the database, return null
            return null;
        }
    }

    @Override
    public boolean checkUniversityEmail(String email)
    {
        try
        {
            String sql = "SELECT `id` FROM `university` WHERE `email` = ?;";
            return template.queryForList(sql, email).isEmpty();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUniversity(University university)
    {
        try
        {
            // Query if registered university already exists in database
            String querySql = "SELECT `email` FROM `university` WHERE `email` = ?;";
            Map<String, Object> result = template.queryForMap(querySql, university.getEmail());
            // If the registered university already exists in database, return true
            if (result.get("email") != null) return true;
        }
        catch (DataAccessException e)
        {
            String sql = "INSERT INTO `university` (`email`, `password`, `name`) VALUES (?, ?, ?);";
            // Add new registered university into database
            template.update(sql, university.getEmail(), university.getPassword(), university.getName());
            return false;
        }
        return false;
    }

    @Override
    public String findNameByEmail(String email)
    {
        final String query = "SELECT `name` FROM `university` WHERE `email` = ?";
        return template.queryForList(query, email).get(0).get("name").toString();
    }
}
