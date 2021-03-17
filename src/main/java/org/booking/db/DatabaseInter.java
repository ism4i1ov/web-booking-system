package org.booking.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface DatabaseInter<T> {

    default Connection getConnection() throws SQLException {
        String url = System.getenv("DB_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");
        return DriverManager.getConnection(url, username, password);
    }

    // CRUD -> Create Read(Selected) Update Delete

    int create(T t);

    boolean delete(String id);

    List<T> getAll();

    Optional<T> getById(String id);

    boolean update(T t);
}
