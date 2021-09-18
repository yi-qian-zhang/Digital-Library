package web;

import domain.Paper;
import service.PaperService;
import service.impl.PaperServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet is for finding and displaying papers for login university.
 */
@WebServlet("/servlet/findMyPapersServlet")
public class FindMyPapers extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        // Query papers corresponding to the login university
        PaperService paperService = new PaperServiceImpl();
        List<Paper> myPapers = paperService.findMyPapers(req.getSession().getAttribute("email"));
        // Store papers in session and redirect
        req.getSession().setAttribute("myPapers", myPapers);
        resp.sendRedirect(req.getContextPath() + "/library/manage.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        this.doPost(req, resp);
    }
}
