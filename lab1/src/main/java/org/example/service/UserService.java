package org.example.service;


import org.example.dao.UserDAO;
import org.example.model.User;

import javax.jws.WebMethod;
import javax.jws.WebService;

import java.sql.SQLException;
import java.util.List;

@WebService
public class UserService {

    private UserDAO userDAO;

    public UserService() {
        try {

            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @WebMethod
    public List<User> searchUsers(String name, Integer age, String city, Double averageScore) {
        try {
            return userDAO.findUsers(name, age, city, averageScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
