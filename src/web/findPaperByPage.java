package web;

import domain.PageBean;
import domain.Paper;
import service.PaperService;
import service.impl.PaperServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is for finding and displaying papers in each page on the website.
 */
@WebServlet("/servlet/findPaperByPageServlet")
public class findPaperByPage extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        // Obtain parameters from browser
        String currentPage = req.getParameter("currentPage"); // Current page
        String rows = req.getParameter("rows"); // Total number of papers each page
        String condition = req.getParameter("w");
        // Handle situations where parameters from browsers are null
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        if (rows == null || "".equals(rows))
            rows = "5";
        // Obtain pageBean object
        PaperService paperService = new PaperServiceImpl();
        PageBean<Paper> pb = paperService.findPaperByPage(currentPage, rows, condition);
        // Store pageBean object and search condition in session, then redirect
        req.getSession().setAttribute("pb", pb);
        req.getSession().setAttribute("condition", condition);
        resp.sendRedirect(req.getContextPath() + "/library/");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        this.doPost(req, resp);
    }
}
