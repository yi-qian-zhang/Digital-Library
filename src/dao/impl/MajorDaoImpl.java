package dao.impl;

import dao.MajorDao;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MajorDaoImpl implements MajorDao
{
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<String> findMajors()
    {
        List<String> majors = new ArrayList<>();
        // Find all majors
        String sql = "SELECT `name` FROM `major`;";
        List<Map<String, Object>> majorMaps = template.queryForList(sql);
        for (Map<String, Object> majorMap : majorMaps)
            majors.add((String) majorMap.get("name"));
        return majors;
    }
}
