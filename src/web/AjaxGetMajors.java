package web;

import service.MajorService;
import service.impl.MajorServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is used for getting available majors by AJAX from the database.
 */
@WebServlet("/servlet/AjaxGetMajors")
public class AjaxGetMajors extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        MajorService majorService = new MajorServiceImpl();
        StringBuilder result = new StringBuilder();
        // The findMajors() method returns a List<String>, so traverse it to get them all
        for (String major : majorService.findMajors())
        {
            // Append the string with the tag of <option></option>
            result.append("<option>").append(major).append("</option>");
        }
        // Response the appended HTML code
        resp.getWriter().write(result.toString());
    }
}
