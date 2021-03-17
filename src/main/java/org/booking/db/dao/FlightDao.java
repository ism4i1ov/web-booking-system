package org.booking.db.dao;

import org.booking.db.DatabaseInter;
import org.booking.entity.Flight;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements DatabaseInter<Flight> {

    @Override
    public int create(Flight flight) {
        String sql = "insert into flight values(default, ?, ?, ?, ?, ?, ?) returning id";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, flight.getAirlineName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(flight.getDepartDateTime()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(flight.getArrivalDateTime()));
            preparedStatement.setString(4, flight.getFlightFrom());
            preparedStatement.setString(5, flight.getDestination());
            preparedStatement.setInt(6, flight.getFreePlaces());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    @Override
    public boolean delete(String id) {
        String sql = "delete from flight where id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Flight> getAll() {
        String sql = "select * from flight order by id";
        List<Flight> flightList = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                flightList.add(getFlightValues(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flightList;
    }

    @Override
    public Optional<Flight> getById(String id) {
        String sql = "select * from flight where id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.of(getFlightValues(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Flight> getFlightByDestDateTicketCount(String destination, LocalDateTime date, String ticketCount) {
        String sql = "select * from flight where destination = ?  and free_places >= ?";
        List<Flight> flightList = new ArrayList<>();
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, destination);
            preparedStatement.setString(2, ticketCount);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                flightList.add(getFlightValues(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return flightList;
    }

    @Override
    public boolean update(Flight flight) {
        String sql = "update flight " +
                "set airline_name = ?, flight_from = ?, destination = ?, free_places = ?, depart_date = ?, arrival_date = ?" +
                "where id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setString(1, flight.getAirlineName());
            preparedStatement.setString(2, flight.getFlightFrom());
            preparedStatement.setString(3, flight.getDestination());
            preparedStatement.setInt(4, flight.getFreePlaces());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(flight.getDepartDateTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(flight.getArrivalDateTime()));
            preparedStatement.setInt(7, flight.getId());
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    private Flight getFlightValues(ResultSet resultSet) throws SQLException {
        int idFlight = resultSet.getInt("id");
        String airlineName = resultSet.getString("airline_name");
        String flightFrom = resultSet.getString("flight_from");
        String destination = resultSet.getString("destination");
        int freePlacesInPlain = resultSet.getInt("free_places");
        LocalDateTime departDateTime = resultSet.getTimestamp("depart_date").toLocalDateTime();
        LocalDateTime arriveDateTime = resultSet.getTimestamp("arrival_date").toLocalDateTime();
        return new Flight(idFlight, airlineName, departDateTime, arriveDateTime, flightFrom, destination, freePlacesInPlain);
    }
}
