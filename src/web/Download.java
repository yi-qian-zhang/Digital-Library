package web;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * This servlet is for downloading papers.
 */
@WebServlet("/servlet/downloadServlet")
public class Download extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        // Obtain the parameter from browser (file name)
        String filename = req.getParameter("filename");
        // Find the server path of the file
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/papers/" + filename);
        // Use byte input stream to load files into memory
        FileInputStream fileInputStream = new FileInputStream(realPath);
        // Set response header
        String mimeType = servletContext.getMimeType(filename);
        resp.setHeader("Content-Type", mimeType);
        resp.setHeader("Content-Disposition", "attachment;filename=" + filename);
        // Write data in input stream into output stream
        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] buffer = new byte[1024 * 8];
        int length = 0;
        while ((length = fileInputStream.read(buffer)) != -1)
            outputStream.write(buffer, 0, length);

        // Close input stream
        fileInputStream.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }
}
