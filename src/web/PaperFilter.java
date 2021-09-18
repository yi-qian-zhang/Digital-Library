package web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This servlet is for filtering requests and if the user is trying to access papers without login,
 * the user will be redirected.
 */
@WebFilter(filterName = "PaperFilter", urlPatterns = {"/papers/*"})
public class PaperFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // HttpSession session = request.getSession();
        String referer = request.getHeader("Referer");
        // If user access paper while not through "Download" button, redirect
        if (referer == null || "".equals(referer) || !referer.startsWith("http://localhost:8080/"))
            response.sendRedirect(request.getContextPath() + "/library/index.jsp");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }
}
