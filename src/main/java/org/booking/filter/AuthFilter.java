package org.booking.filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String requestURI = req.getRequestURI();
        boolean loggedIn = isCookie(req);

        if (requestURI.startsWith("/login") && !loggedIn) filterChain.doFilter(req, resp);
        else if (requestURI.startsWith("/login") && loggedIn) {
            resp.sendRedirect("/user_panel");
            filterChain.doFilter(req, resp);
        } else if (!loggedIn) {
            resp.sendRedirect("/login");
            filterChain.doFilter(req, resp);
        } else filterChain.doFilter(req, resp);
    }

    private boolean isCookie(HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("uid")) return true;
        }
        return false;
    }
}
