package org.example.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.example.dao.UserDAO;
import org.example.model.User;

import java.sql.SQLException;
import java.util.List;

@WebService(endpointInterface = "org.example.service.UserService")
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl() {
        try {

            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @WebMethod
    public List<User> searchUsers(String name, Integer age, String city, Double averageScale) {
        try {
            return userDAO.findUsers(name, age, city, averageScale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int createUser(String name, int age, String city, Double averageScale) {
       try {
          return userDAO.createUser(name, age, city, averageScale);
       } catch (SQLException e) {
           e.printStackTrace();
       }
       return -1;
    }

    @Override
    public boolean deleteUser(int id) {
        try {
            return userDAO.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateUser(int id, String name, int age, String city, Double averageScale) {
        try {
            return userDAO.updateUser(id, name, age, city, averageScale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
