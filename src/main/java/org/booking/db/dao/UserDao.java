package org.booking.db.dao;

import org.booking.db.DatabaseInter;
import org.booking.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements DatabaseInter<User> {

    @Override
    public int create(User user) {
        String sql = "insert into " +
                "\"user\" values(default,?,?,?,?) returning id";
        try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());
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
        String sql = "delete from \"user\" where id = " + id;
        try (Statement statement = connection().createStatement()) {
            return statement.execute(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from \"user\"";
        List<User> userList = new ArrayList<>();
        try (Statement statement = connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                userList.add(getUserValues(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<User> getById(String id) {
        String sql = "select * from \"user\" where id = " + id;
        try (Statement statement = connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            return Optional.of(getUserValues(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public boolean update(User user) {
        String sql = "update \"user\"" +
                "set first_name = ?, last_name = ?, user_password = ?" +
                "where id = " + user.getId();
        try (PreparedStatement preparedStatement = connection().prepareStatement(sql)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getPassword());
            return preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public Optional<User> getByUsernameAndPassword(String username, String password) {
        String sql = "select * from \"user\" where username = '" + username + "' and user_password = '" + password + "'";
        try (Statement statement = connection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                return Optional.of(getUserValues(resultSet));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    private User getUserValues(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String firstName = resultSet.getString("first_name");
        String lastName = resultSet.getString("last_name");
        String username = resultSet.getString("username");
        String password = resultSet.getString("user_password");
        return new User(id, username, password, firstName, lastName);
    }
}
