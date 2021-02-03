package org.booking.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Flight implements Serializable {
    private final int id;
    private String airlineName;
    private LocalDateTime departDateTime;
    private LocalDateTime arrivalDateTime;
    private String flightFrom;
    private String destination;
    private int freePlaces;
    public static final long serialVersionUID = 1L;


    public Flight(int id, String airlineName, LocalDateTime departDateTime, LocalDateTime arrivalDateTime, String flightFrom, String destination, int freePlaces) {
        this.id = id;
        this.airlineName = airlineName;
        this.departDateTime = departDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.flightFrom = flightFrom;
        this.destination = destination;
        this.freePlaces = freePlaces;
    }

    public LocalDateTime getDepartDateTime() {
        return departDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public int getId() {
        return id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }

    public String getArrivalDateTimeString() {
        return arrivalDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getDepartDateTimeString() {
        return departDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    public void setDepartDateTime(LocalDateTime departDateTime) {
        this.departDateTime = departDateTime;
    }

    public String getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(String flightFrom) {
        this.flightFrom = flightFrom;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getFreePlaces() {
        return freePlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return id == flight.id &&
                freePlaces == flight.freePlaces &&
                Objects.equals(departDateTime, flight.departDateTime) &&
                Objects.equals(arrivalDateTime, flight.arrivalDateTime) &&
                Objects.equals(flightFrom, flight.flightFrom) &&
                Objects.equals(destination, flight.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departDateTime, arrivalDateTime, flightFrom, destination, freePlaces);
    }

    public String getInfo() {
        return String.format("ID = %d, airline name = %s, flight from = %s, destination = %s, depart date = %s, arrival date = %s free places in the airplane = %d", id, airlineName, flightFrom, destination, departDateTime.format(DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm")), arrivalDateTime.format(DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm")), freePlaces);
    }

    @Override
    public String toString() {
        return String.format("Flight id = %d, depart time = %s, destination = %s", id, departDateTime.format(DateTimeFormatter.ofPattern("dd.MMM.yyyy HH:mm")), destination);
    }
}
