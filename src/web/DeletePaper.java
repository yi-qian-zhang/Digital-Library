package web;

import service.PaperService;
import service.impl.PaperServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is for deleting papers by university.
 */
@WebServlet("/servlet/deletePaperServlet")
public class DeletePaper extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // Obtain paper id from browser
        String id = req.getParameter("id");
        // Delete paper with given id
        PaperService paperService = new PaperServiceImpl();
        paperService.deletePaper(id);
        // Redirect to findMyPaperServlet
        resp.sendRedirect(req.getContextPath()+"/servlet/findMyPapersServlet");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }
}
