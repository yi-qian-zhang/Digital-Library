package web;

import domain.Reader;
import domain.Reviewer;
import domain.University;
import service.impl.ReaderServiceImpl;
import service.impl.ReviewerServiceImpl;
import service.impl.UniversityServiceImpl;
import service.impl.UserLogServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * This servlet is used for user sign in.
 * It configures session and returns "succeed" when the email and password are correct.
 * Otherwise, it responses "passwordError" or "error" when the sign in information is invalid
 */
@WebServlet("/servlet/AjaxSignIn")
public class AjaxSignIn extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        // Get the current time for logging
        final String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
        // Set encoding and get parameters from request
        req.setCharacterEncoding("utf-8");
        final String identity = req.getParameter("identity");
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");
        final PrintWriter writer = resp.getWriter();

        // For the identities of university, reviewer, and reader, check the email and password
        switch (identity)
        {
            // When the identity is university
            case "university":
                University university = new University();
                university.setEmail(email);
                university.setPassword(password);
                // The login() method returns null when the email or password is incorrect
                if (new UniversityServiceImpl().login(university) == null)
                {
                    // Response "passwordError" and exit
                    writer.write("passwordError");
                    return;
                }
                break;
            // When the identity is reviewer
            case "reviewer":
                Reviewer reviewer = new Reviewer();
                reviewer.setEmail(email);
                reviewer.setPassword(password);
                // The login() method returns null when the email or password is incorrect
                if (new ReviewerServiceImpl().login(reviewer) == null)
                {
                    // Response "passwordError" and exit
                    writer.write("passwordError");
                    return;
                }
                break;
            // When the identity is reader
            case "reader":
                Reader reader = new Reader();
                reader.setEmail(email);
                reader.setPassword(password);
                // The login() method returns null when the email or password is incorrect
                if (new ReaderServiceImpl().login(reader) == null)
                {
                    // Response "passwordError" and exit
                    writer.write("passwordError");
                    return;
                }
                break;
            // Received other identities
            default:
                // Response "error" and exit
                writer.write("error");
                return;
        }

        // Only when the password of the email is correct, the following code will be executed
        // Log sign in information
        new UserLogServiceImpl().logSignIn(time, identity, email, req.getRemoteAddr());
        // Get session and set attributes
        HttpSession session = req.getSession();
        session.setAttribute("USER_SESSION", session.getId());
        session.setAttribute("identity", identity);
        session.setAttribute("email", email);
        // Response "succeed"
        writer.write("succeed");
    }
}
