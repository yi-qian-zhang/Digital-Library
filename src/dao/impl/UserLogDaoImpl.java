package dao.impl;

import dao.UserLogDao;
import org.springframework.jdbc.core.JdbcTemplate;
import util.JDBCUtils;

public class UserLogDaoImpl implements UserLogDao
{
    private final JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void logSignIn(String time, String identity, String email, String ip)
    {
        final String operation = "SIGN_IN";
        insertLog(time, operation, identity, email, ip);
    }

    @Override
    public void logSignOut(String time, String identity, String email, String ip)
    {
        final String operation = "SIGN_OUT";
        insertLog(time, operation, identity, email, ip);
    }

    @Override
    public void logSignUp(String time, String identity, String email, String ip)
    {
        final String operation = "SIGN_UP";
        insertLog(time, operation, identity, email, ip);
    }

    private void insertLog(String time, String operation, String identity, String email, String ip)
    {
        final String query = "INSERT INTO `UserLog` (`time`, `operation`, `identity`, `email`, `ip`) " +
                "VALUES (?, ?, ?, ?, ?);";
        template.update(query, time, operation, identity, email, ip);
    }
}
