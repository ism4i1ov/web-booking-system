package org.booking.controller;

import freemarker.template.TemplateException;
import org.booking.constant.TemplateEngine;
import org.booking.entity.Booking;
import org.booking.entity.User;
import org.booking.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class FlightServlet extends HttpServlet {
    private final TemplateEngine templateEngine;
    private final BookingService bookingService;

    public FlightServlet(TemplateEngine templateEngine, BookingService bookingService) {
        this.templateEngine = templateEngine;
        this.bookingService = bookingService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        String id = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("uid")) {
                id = cookie.getValue();
                break;
            }
        }
        Optional<User> userById = bookingService.getUserById(id);
        List<Booking> bookingsIdByUserId = bookingService.getBookingsIdByUserId(String.valueOf(userById.get().getId()));
        HashMap<String, Object> data = new HashMap<>();
        data.put("bookings", bookingsIdByUserId);
        data.put("user", userById.get());

        try {
            templateEngine.render("flights.ftl", data, resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] bookingId = req.getParameterValues("remove_booking");
        String id = bookingId[0];
        bookingService.cancelBooking(id);
        resp.sendRedirect("/user_panel");
    }
}
