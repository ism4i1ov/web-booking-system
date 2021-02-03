package org.booking.entity;

import java.io.Serializable;
import java.util.Objects;

public class Booking implements Serializable {

    private final int id;
    private final int flightId;
    private int ticketCount;
    private int userId;
    public static final long serialVersionUID = 1L;

    public Booking(int id, int flightId, int ticketCount, int userId) {
        this.id = id;
        this.flightId = flightId;
        this.ticketCount = ticketCount;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public int getFlightId() {
        return flightId;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return id == booking.id &&
                flightId == booking.flightId &&
                ticketCount == booking.ticketCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flightId, ticketCount);
    }

    @Override
    public String toString() {
        return String.format("Booking id = %d, flight id = %d, ticket count = %d", id, flightId, ticketCount);
    }
}
