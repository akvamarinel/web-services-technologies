package org.example.dao.connection.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";  // Замените на вашу БД
    private static final String USER = "postgres";  // Имя пользователя
    private static final String PASSWORD = "postgres";  // Пароль

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("PostgreSQL driver not found!");
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
