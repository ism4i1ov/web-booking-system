package org.booking.db.dao;

import org.booking.db.DatabaseInter;
import org.booking.entity.Booking;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingDao implements DatabaseInter<Booking> {

    @Override
    public int create(Booking booking) {
        String sql = "insert into " +
                "booking values(default,?,?,?);";
        String sql2 = "SELECT LAST_INSERT_ID();";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, booking.getFlightId());
            preparedStatement.setInt(2, booking.getTicketCount());
            preparedStatement.setInt(3, booking.getUserId());
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.executeQuery(sql2);
            resultSet.next();
            return resultSet.getInt("LAST_INSERT_ID()");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean delete(String id) {
        String sql = "delete from booking where id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Booking> getAll() {
        String sql = "select * from booking";
        List<Booking> bookingList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                bookingList.add(getBookingValues(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookingList;
    }

    @Override
    public Optional<Booking> getById(String id) {
        String sql = "select * from booking where id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getBookingValues(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Booking booking) {
        String sql = "update booking set ticket_count = ? where id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, booking.getTicketCount());
            preparedStatement.setInt(2, booking.getId());
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

//    public int bookFlight(int bookingId, int userId) {
//        String sql = "insert into booking values(default, ?, ?, ?) returning id";
//        try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
//            preparedStatement.setInt(1, bookingId);
//            preparedStatement.setInt(2, userId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            resultSet.next();
//            return resultSet.getInt("id");
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        return -1;
//    }

    public List<Booking> getBookingsByUserId(String id) {
        String sql = "select b.* from booking b left join users u on u.id = b.user_id where u.id = ?";
        List<Booking> bookingsId = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                bookingsId.add(getBookingValues(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return bookingsId;
    }

    private Booking getBookingValues(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int flightId = resultSet.getInt("flight_id");
        int ticketCount = resultSet.getInt("ticket_count");
        int userId = resultSet.getInt("user_id");
        return new Booking(id, flightId, ticketCount, userId);
    }


}
