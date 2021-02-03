package org.booking.db.dao;

import org.booking.db.DatabaseInter;
import org.booking.entity.Flight;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements DatabaseInter<Flight> {

    @Override
    public int create(Flight flight) {
        String sql = "insert into flight values(default, ?, ?, ?, ?, ?, ?) returning id";
        try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
            preparedStatement.setString(1, flight.getAirlineName());
            preparedStatement.setString(2, flight.getFlightFrom());
            preparedStatement.setString(3, flight.getDestination());
            preparedStatement.setInt(4, flight.getFreePlaces());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(flight.getDepartDateTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(flight.getArrivalDateTime()));
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
        String sql = "delete from flight where id = " + id;
        try (Statement statement = connection().createStatement()) {
            return statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Flight> getAll() {
        String sql = "select * from flight order by id";
        List<Flight> flightList = new ArrayList<>();
        try (Statement statement = connection().createStatement()) {
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
        String sql = "select * from flight where id = " + id;
        try (Statement statement = connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Optional.of(getFlightValues(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Flight> getFlightByDestDateTicketCount(String destination, String date, String ticketCount) {
        String sql = "select * from flight where destination = '" + destination + "' and free_places_in_plain >= " + ticketCount;
        List<Flight> flightList = new ArrayList<>();
        try (Statement statement = connection().createStatement()) {
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
    public boolean update(Flight flight) {
        String sql = "update flight " +
                "set airline_name = ?, flight_from = ?, destination = ?, free_places_in_plain = ?, depart_date_time = ?, arrive_date_time = ?" +
                "where id = " + flight.getId();
        try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
            preparedStatement.setString(1, flight.getAirlineName());
            preparedStatement.setString(2, flight.getFlightFrom());
            preparedStatement.setString(3, flight.getDestination());
            preparedStatement.setInt(4, flight.getFreePlaces());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(flight.getDepartDateTime()));
            preparedStatement.setTimestamp(6, Timestamp.valueOf(flight.getArrivalDateTime()));
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
        int freePlacesInPlain = resultSet.getInt("free_places_in_plain");
        LocalDateTime departDateTime = resultSet.getTimestamp("depart_date_time").toLocalDateTime();
        LocalDateTime arriveDateTime = resultSet.getTimestamp("arrive_date_time").toLocalDateTime();
        return new Flight(idFlight, airlineName, departDateTime, arriveDateTime, flightFrom, destination, freePlacesInPlain);
    }
}
