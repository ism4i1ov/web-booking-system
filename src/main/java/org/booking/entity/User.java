package org.booking.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private final int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private List<Booking> bookingList;
    public static final long serialVersionUID = 1L;

    public User(int id, String username, String password, String name, String surname) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Booking> getBookingList() {
        return bookingList;
    }

    public void setBookingList(List<Booking> bookingList) {
        this.bookingList = bookingList;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(surname, user.surname) &&
                Objects.equals(bookingList, user.bookingList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, name, surname, bookingList);
    }

    @Override
    public String toString() {
        return String.format("Passenger id = %d, name = %s, surname = %s", id, name, surname);
    }
}
