package service.impl;

import dao.UserLogDao;
import dao.impl.UserLogDaoImpl;
import service.UserLogService;

public class UserLogServiceImpl implements UserLogService
{
    private final UserLogDao dao = new UserLogDaoImpl();

    @Override
    public void logSignIn(String time, String identity, String email, String ip)
    {
        dao.logSignIn(time, identity, email, ip);
    }

    @Override
    public void logSignOut(String time, String identity, String email, String ip)
    {
        dao.logSignOut(time, identity, email, ip);
    }

    @Override
    public void logSignUp(String time, String identity, String email, String ip)
    {
        dao.logSignUp(time, identity, email, ip);
    }
}
