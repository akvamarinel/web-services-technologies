package org.example.service;


import jakarta.ws.rs.*;
import org.example.dao.UserDAO;
import org.example.model.User;

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
            @QueryParam("averageScore") Double averageScale) {
        try {
            return userDAO.findUsers(name, age, city, averageScale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @POST
    public int createUser(@QueryParam("name") String name,
                          @QueryParam("age") int age,
                          @QueryParam("city") String city,
                          @QueryParam("averageScale") double averageScale) {
        try {
            return userDAO.createUser(name, age, city, averageScale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    @DELETE
    public boolean deleteUser(@QueryParam("id") int id) {
        try {
            return userDAO.deleteUser(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @PUT
    public boolean updateUser(
            @QueryParam("int") int id,
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale
    ) {
        try {
            return userDAO.updateUser(id, name, age, city, averageScale);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
