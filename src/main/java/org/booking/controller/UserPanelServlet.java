package org.booking.controller;

import freemarker.template.TemplateException;
import org.booking.constant.TemplateEngine;
import org.booking.entity.Flight;
import org.booking.entity.User;
import org.booking.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

public class UserPanelServlet extends HttpServlet {

    private final TemplateEngine templateEngine;
    private final BookingService bookingService;

    public UserPanelServlet(TemplateEngine templateEngine, BookingService bookingService) {
        this.templateEngine = templateEngine;
        this.bookingService = bookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Flight> flightList = bookingService.getOnlineBoard();
        HashMap<String, Object> data = new HashMap<>();
        data.put("flights", flightList);
        Cookie[] cookies = req.getCookies();
        if(cookies == null) return;
        String id = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("uid")) {
                id = cookie.getValue();
                break;
            }
        }
        Optional<User> user = bookingService.getUserById(id);
        data.put("user", user.get());
        try {
            templateEngine.render("user_panel.ftl", data, resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("my_flights") != null) {
            resp.sendRedirect("/user_flights");
        } else if (req.getParameter("logout") != null) {
            Cookie[] cookies = req.getCookies();
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                resp.addCookie(cookie);
            }
            resp.sendRedirect("/login");
        } else {
            resp.sendRedirect("/book_flight");
        }
    }
}
