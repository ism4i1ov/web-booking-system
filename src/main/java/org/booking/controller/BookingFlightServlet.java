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
import java.util.*;

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

            String id = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals("uid"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse("");

            HashMap<String, Object> data = new HashMap<>();
            bookingService.getUserById(id).ifPresent(user -> {
                data.put("user", user);
                data.put("flights", flights);
            });
            try {
                templateEngine.render("book-flight.ftl", data, resp);
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        String id = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("uid"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");

        HashMap<String, Object> data = new HashMap<>();
        bookingService.getUserById(id).ifPresent(user -> {
            data.put("user", user);
            data.put("flights", new ArrayList<Flight>());
        });

        try {
            templateEngine.render("book-flight.ftl", data, resp);
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
