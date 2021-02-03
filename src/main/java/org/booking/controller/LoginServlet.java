package org.booking.controller;

import org.booking.entity.User;
import org.booking.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class LoginServlet extends HttpServlet {
    private final BookingService bookingService;

    public LoginServlet(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Optional<User> optionalUser = bookingService.getUserByUsernameAndPassword(username, password);
        if (optionalUser.isPresent()) {
            String id = String.valueOf(optionalUser.get().getId());
            response.addCookie(new Cookie("uid", id));
            response.sendRedirect("/user_panel");
        } else if (username.equalsIgnoreCase("admin") && password.equals("12345")) response.sendRedirect("/admin");
        else {
            try (OutputStream os = response.getOutputStream()) {
                os.write("Username or password is incorrect!".getBytes());
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (OutputStream os = response.getOutputStream()) {
            Path path = Paths.get("src\\main\\resources\\web\\template\\login.ftl");
            Files.copy(path, os);
        }
    }
}
