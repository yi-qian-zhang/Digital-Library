package web;

import domain.Reader;
import domain.Reviewer;
import domain.University;
import service.ReaderService;
import service.ReviewerService;
import service.UniversityService;
import service.impl.ReaderServiceImpl;
import service.impl.ReviewerServiceImpl;
import service.impl.UniversityServiceImpl;
import service.impl.UserLogServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;

/**
 * This servlet is used for sign up.
 * It returns "succeed" only when the account has been created successfully.
 * Otherwise, it responses "emailError" or "majorError" or "error" according to the invalidations.
 */
@WebServlet("/servlet/AjaxSignUp")
public class AjaxSignUp extends HttpServlet
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
        final String name = req.getParameter("name");
        final String password = req.getParameter("password");
        final String major = req.getParameter("major");
        final PrintWriter writer = resp.getWriter();

        // For the identities of university, reviewer, and reader, check and insert data into the database
        switch (identity)
        {
            // When the identity is university
            case "university":
                University university = new University();
                university.setEmail(email);
                university.setName(name);
                university.setPassword(password);
                UniversityService universityService = new UniversityServiceImpl();
                // The checkUniversityEmail() method returns true only when the email is not duplicated in the table
                if (!universityService.checkUniversityEmail(email))
                {
                    // Response "emailError" when the email is duplicated
                    writer.write("emailError");
                }
                else
                {
                    // The addUniversity() method returns true when query error occurred
                    if (universityService.addUniversity(university))
                    {
                        // Response "error"
                        writer.write("error");
                    }
                    else
                    {
                        // When no problem occurred, log sign up information and response "succeed"
                        new UserLogServiceImpl().logSignUp(time, identity, email, req.getRemoteAddr());
                        writer.write("succeed");
                    }
                }
                break;
            // When the identity is reviewer
            case "reviewer":
                Reviewer reviewer = new Reviewer();
                reviewer.setEmail(email);
                reviewer.setName(name);
                reviewer.setPassword(password);
                reviewer.setMajor(major);
                ReviewerService reviewerService = new ReviewerServiceImpl();
                // The checkReviewerEmail() method returns true only when the email is not duplicated in the table
                if (!reviewerService.checkReviewerEmail(email))
                {
                    // Response "emailError" when the email is duplicated
                    writer.write("emailError");
                }
                else
                {
                    // The checkReviewerMajor() returns true only when the major is in the major table
                    if (!reviewerService.checkReviewerMajor(major))
                    {
                        // Response "majorError" when the major is invalid
                        writer.write("majorError");
                    }
                    else
                    {
                        // The addReviewer() method returns true when query error occurred
                        if (reviewerService.addReviewer(reviewer))
                        {
                            // Response "error"
                            writer.write("error");
                        }
                        else
                        {
                            // When no problem occurred, log sign up information and response "succeed"
                            new UserLogServiceImpl().logSignUp(time, identity, email, req.getRemoteAddr());
                            writer.write("succeed");
                        }
                    }
                }
                break;
            // When the identity is reader
            case "reader":
                Reader reader = new Reader();
                reader.setEmail(email);
                reader.setName(name);
                reader.setPassword(password);
                ReaderService readerService = new ReaderServiceImpl();
                // The checkReaderEmail() method returns true only when the email is not duplicated in the table
                if (!readerService.checkReaderEmail(email))
                {
                    // Response "emailError" when the email is duplicated
                    writer.write("emailError");
                }
                else
                {
                    // The addReader() method returns true when query error occurred
                    if (readerService.addReader(reader))
                    {
                        // Response "error"
                        writer.write("error");
                    }
                    else
                    {
                        // When no problem occurred, log sign up information and response "succeed"
                        new UserLogServiceImpl().logSignUp(time, identity, email, req.getRemoteAddr());
                        writer.write("succeed");
                    }
                }
                break;
            // Received other identities
            default:
                // Response "error"
                writer.write("error");
                break;
        }
    }
}
