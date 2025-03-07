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
    public List<User> searchUsers(String name, Integer age, String city, Double averageScore) {
        try {
            return userDAO.findUsers(name, age, city, averageScore);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
