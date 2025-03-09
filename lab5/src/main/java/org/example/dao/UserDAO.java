package org.example.dao;

import org.example.dao.connection.database.DatabaseConnection;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;
    private static final String FIND_USERS = "SELECT * FROM users WHERE 1=1";
    private static final String FILTER_NAME = " AND name = ?";
    private static final String FILTER_AGE = " AND age = ?";
    private static final String FILTER_CITY = " AND city = ?";
    private static final String FILTER_AVERAGE_SCORE = " AND average_scale = ?";

    private static final String CREATE_USER = "INSERT INTO users (name, age, city, average_scale) VALUES (?,?, ?,?);";
    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?;";
    private static final String UPDATE_USER = "UPDATE users SET name = ?, age = ?, city = ?, average_scale = ? WHERE id = ?";



    public UserDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }


    public List<User> findUsers(String name, Integer age, String city, Double averageScale) throws SQLException {
        StringBuilder queryBuilder = new StringBuilder(FIND_USERS);
        if (name != null) {
            queryBuilder.append(FILTER_NAME);
        }
        if (age != null) {
            queryBuilder.append(FILTER_AGE);
        }
        if (city != null) {
            queryBuilder.append(FILTER_CITY);
        }
        if (averageScale != null) {
            queryBuilder.append(FILTER_AVERAGE_SCORE);
        }
        PreparedStatement statement = connection.prepareStatement(queryBuilder.toString());
        int pos = 1;
        if (name != null) {
            statement.setString(pos++, name);
        }
        if (age != null) {
            statement.setInt(pos++, age);
        }
        if (city != null) {
            statement.setString(pos++, city);
        }
        if (averageScale != null) {
            statement.setDouble(pos, averageScale);
        }
        ResultSet res = statement.executeQuery();
        List<User> userResult = new ArrayList<>();
        while (res.next()) {
            User user = new User();
            user.setId(res.getInt("id"));
            user.setName(res.getString("name"));
            user.setAge(res.getInt("age"));
            user.setCity(res.getString("city"));
            user.setAverageScale(res.getDouble("average_scale"));
            userResult.add(user);
        }
        return userResult;
    }

    public int createUser(String name, Integer age, String city, Double averageScale) throws SQLException {
        if (name == null || age == null || city == null || averageScale == null) {
            return -1;
        }

        PreparedStatement statement = connection.prepareStatement(CREATE_USER, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, name);
        statement.setInt(2, age);
        statement.setString(3, city);
        statement.setDouble(4, averageScale);
        statement.executeUpdate();
        var res = statement.getGeneratedKeys();
        if (res.next()) {
            return res.getInt(1);

        } else {
            throw new SQLException("Failed to insert user");
        }
    }

    public boolean deleteUser(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(DELETE_USER);
        statement.setInt(1, id);
        return statement.executeUpdate() > 0;
    }

    public boolean updateUser(int id,String name, Integer age, String city, Double averageScore) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(UPDATE_USER);
        statement.setString(1, name);
        statement.setInt(2, age);
        statement.setString(3, city);
        statement.setDouble(4, averageScore);
        statement.setInt(5, id);
        return statement.executeUpdate() > 0;
    }
}