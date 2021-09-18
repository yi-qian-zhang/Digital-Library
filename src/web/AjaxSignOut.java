package web;

import service.impl.UserLogServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

/**
 * This servlet is used for user sign in.
 * The response text of the servlet is always nothing.
 */
@WebServlet("/servlet/AjaxSignOut")
public class AjaxSignOut extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
    {
        // Get the current time for logging
        final String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
        // Get the session
        HttpSession session = req.getSession();

        // The attribute "USER_SESSION" is not null when user has signed in
        if (session.getAttribute("USER_SESSION") != null)
        {
            // Get the attributes and log sign out information
            final String identity = session.getAttribute("identity").toString();
            final String email = session.getAttribute("email").toString();
            new UserLogServiceImpl().logSignOut(time, identity, email, req.getRemoteAddr());
            // The invalidate() method ends the session and clears all the attributes
            // Then the server will start a new session with a different session ID
            // So all the other operations about session must be finished before executing the invalidate() method
            session.invalidate();
        }
    }
}
