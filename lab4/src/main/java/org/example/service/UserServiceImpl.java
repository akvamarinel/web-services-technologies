package org.example.service;

import org.example.dao.UserDAO;
import org.example.model.User;

import jakarta.ws.rs.*;
import java.sql.SQLException;
import java.util.List;

@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    public UserServiceImpl() {
        try {
            this.userDAO = new UserDAO();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @GET
    @Override
    @Path("/search")
    public List<User> searchUsers(
            @QueryParam("name") String name,
            @QueryParam("age") Integer age,
            @QueryParam("city") String city,
            @QueryParam("averageScore") Double averageScore
    ) {
        try {
            return userDAO.findUsers(name, age, city, averageScore);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
