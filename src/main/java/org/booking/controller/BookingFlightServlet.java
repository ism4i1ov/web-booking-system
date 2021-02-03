package org.booking.controller;

import freemarker.template.TemplateException;
import org.booking.constant.TemplateEngine;
import org.booking.entity.Flight;
import org.booking.entity.User;
import org.booking.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BookingFlightServlet extends HttpServlet {

    private final TemplateEngine templateEngine;
    private final BookingService bookingService;

    public BookingFlightServlet(TemplateEngine templateEngine, BookingService bookingService) {
        this.templateEngine = templateEngine;
        this.bookingService = bookingService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("search") != null) {
            String destination = req.getParameter("destination");
            String ticketCount = req.getParameter("ticket_count");
            List<Flight> flights = bookingService.getFlightByDestDateTicketCount(destination, null, ticketCount);
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
            HashMap<String, Object> data = new HashMap<>();
            data.put("user", userById.get());
            data.put("flights", flights);
            try {
                templateEngine.render("bookflight.ftl", data, resp);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
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
        HashMap<String, Object> data = new HashMap<>();
        data.put("user", userById.get());
        data.put("flights", new ArrayList<Flight>());
        try {
            templateEngine.render("bookflight.ftl", data, resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
