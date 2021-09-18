package web;

import service.PaperService;
import service.impl.PaperServiceImpl;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is for handling evaluation and review of paper by login reviewer.
 */
@WebServlet("/servlet/peerReviewServlet")
public class PeerReview extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // Get parameters from user browser
        String comment = req.getParameter("comment");
        String isAccept = req.getParameter("accept");
        String paperId = req.getParameter("id");
        // Find the servlet context
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/");
        // Update the paper being reviewed in the database
        PaperService paperService = new PaperServiceImpl();
        paperService.reviewPaper(comment, isAccept, paperId,
                (String)req.getSession().getAttribute("email"), realPath);
        // Forward
        req.getRequestDispatcher("/servlet/findReviewServlet").forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }
}
