package org.example.service;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
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
    public Response searchUsers(
            @QueryParam("name") String name,
            @QueryParam("age") Integer age,
            @QueryParam("city") String city,
            @QueryParam("averageScore") Double averageScale) {
        try {
            return Response.status(Response.Status.OK).entity(userDAO.findUsers(name, age, city, averageScale)).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @Override
    @POST
    public Response createUser(@QueryParam("name") String name,
                               @QueryParam("age") int age,
                               @QueryParam("city") String city,
                               @QueryParam("averageScale") double averageScale) {
        try {
            int res = userDAO.createUser(name, age, city, averageScale);
            if (res > 0) {
                return Response.status(Response.Status.CREATED).entity(res).build();
            }
            return Response.serverError().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @Override
    @DELETE
    public Response deleteUser(@QueryParam("id") int id) {
        try {
            boolean res = userDAO.deleteUser(id);
            if (res) {
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

    @Override
    @PUT
    public Response updateUser(
            @QueryParam("int") int id,
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale
    ) {
        try {
            boolean res = userDAO.updateUser(id, name, age, city, averageScale);
            if (res) {
                return Response.status(Response.Status.OK).entity(true).build();
            }
            return Response.serverError().build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }

}
