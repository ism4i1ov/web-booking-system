package org.booking;

import org.booking.constant.TemplateEngine;
import org.booking.controller.FlightServlet;
import org.booking.controller.LoginServlet;
import org.booking.controller.StaticServlet;
import org.booking.controller.UserPanelServlet;
import org.booking.db.dao.BookingDao;
import org.booking.db.dao.FlightDao;
import org.booking.db.dao.UserDao;
import org.booking.filter.AuthFilter;
import org.booking.service.BookingService;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = new Server(80);

        TemplateEngine templateEngine = TemplateEngine.folder("template");

        ServletContextHandler handler = new ServletContextHandler();
        BookingService bookingService = new BookingService(new FlightDao(), new UserDao(), new BookingDao());

        handler.addServlet(new ServletHolder(new LoginServlet(bookingService)), "/login");
        handler.addServlet(new ServletHolder(new UserPanelServlet(templateEngine, bookingService)), "/user_panel");
        handler.addServlet(new ServletHolder(new FlightServlet(templateEngine, bookingService)), "/user_flights");
        handler.addServlet(new ServletHolder(new StaticServlet("css")), "/css/*");
        handler.addServlet(new ServletHolder(new StaticServlet("js")), "/js/*");
        handler.addServlet(new ServletHolder(new StaticServlet("image")), "/image/*");

        handler.addFilter(AuthFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(handler);
        server.join();
        server.start();
    }
}
