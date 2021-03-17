package org.booking.controller;

import freemarker.template.TemplateException;
import org.booking.constant.TemplateEngine;
import org.booking.entity.Booking;
import org.booking.entity.Flight;
import org.booking.entity.User;
import org.booking.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        Cookie[] cookies = req.getCookies();
        if (cookies == null) return;
        String id = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("uid"))
                .findFirst()
                .map(Cookie::getValue)
                .orElse("");

        if (req.getParameter("search") != null) {
            String destination = req.getParameter("destination");
//            String date = req.getParameter("depart_date");
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
//            LocalDateTime departDate = LocalDateTime.parse(date, formatter);
            String ticketCount = req.getParameter("ticket_count");
            List<Flight> flights = bookingService.getFlightByDestDateTicketCount(destination, null, ticketCount);

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
        } else if (req.getParameter("booking") != null) {
            String flightId = req.getParameter("flight_id");
            String ticketCount = req.getParameter("ticket_count");
            bookingService.bookFlight(Integer.parseInt(id), Integer.parseInt(flightId), Integer.parseInt(ticketCount));
            resp.sendRedirect("/user_flights");
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
