package org.example.service;


import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import org.example.dao.UserDAO;
import org.example.exception.ServiceFault;
import org.example.exception.UserServiceException;
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
    public List<User> searchUsers(String name, Integer age, String city, Double averageScale) throws UserServiceException {
        try {
            return userDAO.findUsers(name, age, city, averageScale);
        } catch (SQLException e) {
            throw new UserServiceException("invalid attempt to find users", ServiceFault.newServiceException(e.getMessage()));
        }
    }

    @Override
    public int createUser(String name, int age, String city, Double averageScale) throws UserServiceException {
        try {
            return userDAO.createUser(name, age, city, averageScale);
        } catch (SQLException e) {
            throw new UserServiceException("invalid attempt to create user", ServiceFault.newServiceException(e.getMessage()));
        }
    }

    @Override
    public boolean deleteUser(int id) throws UserServiceException {
        boolean rowsDeleted;
        try {
            rowsDeleted =  userDAO.deleteUser(id);
        } catch (SQLException e) {
            throw new UserServiceException("invalid attempt to delete user", ServiceFault.newServiceException(e.getMessage()));
        }
        if (!rowsDeleted) {
            System.out.println("here! delete user failed");
            throw new UserServiceException("invalid attempt to delete user", ServiceFault.newServiceException("User does not exist"));
        }
        else return true;
    }

    @Override
    public boolean updateUser(int id, String name, int age, String city, Double averageScale) throws UserServiceException {
        try {
            return userDAO.updateUser(id, name, age, city, averageScale);
        } catch (SQLException e) {
            throw new UserServiceException("invalid attempt to update user", ServiceFault.newServiceException(e.getMessage()));
        }
    }

}
