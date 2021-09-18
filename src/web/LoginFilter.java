package web;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * This filter is for filtering requests and if user is trying to access webpages without login,
 * user will be redirected.
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = {"/library/index.jsp", "/library/manage.jsp",
        "/library/upload.jsp", "/library/review.jsp"})
public class LoginFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        // If the user has not login, redirect to login page
        if (session.getAttribute("USER_SESSION") == null)
            response.sendRedirect(request.getContextPath() + "/library/login.jsp");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() { }
}
