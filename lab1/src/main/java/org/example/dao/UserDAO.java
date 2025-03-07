package org.example.dao;

import org.example.dao.connection.database.DatabaseConnection;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;
    private static final String FIND_USERS = "SELECT * FROM users WHERE 1=1";
    private static final String FILTER_NAME = "AND name = ?";
    private static final String FILTER_AGE = "AND age = ?";
    private static final String FILTER_CITY = "AND city = ?";
    private static final String FILTER_AVERAGE_SCORE = "AND average_score = ?";


    public UserDAO() throws SQLException {
        this.connection = DatabaseConnection.getConnection();
    }


    public List<User> findUsers(String name, Integer age, String city, Double averageScore) throws SQLException {
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
        if (averageScore != null) {
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
        if (averageScore != null) {
            statement.setDouble(pos, averageScore);
        }
        ResultSet res = statement.executeQuery();
        List<User> userResult = new ArrayList<>();
        while (res.next()) {
            User user = new User();
            user.setId(res.getInt("id"));
            user.setName(res.getString("name"));
            user.setAge(res.getInt("age"));
            user.setCity(res.getString("city"));
            user.setAverageScore(res.getDouble("average_score"));
            userResult.add(user);
        }
        return userResult;
    }
}