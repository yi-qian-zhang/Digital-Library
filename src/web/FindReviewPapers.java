package web;

import domain.Paper;
import service.PaperService;
import service.impl.PaperServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * This servlet if for finding and displaying papers waiting to be reviewed for login reviewer.
 */
@WebServlet("/servlet/findReviewServlet")
public class FindReviewPapers extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // Query papers waiting to be reviewed given reviewer email
        PaperService paperService = new PaperServiceImpl();
        List<Paper> reviewPapers = paperService.findReviewPapers(req.getSession().getAttribute("email"));
        // Store papers in session and redirect
        req.getSession().setAttribute("reviewPapers", reviewPapers);
        resp.sendRedirect(req.getContextPath() + "/library/review.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }
}
