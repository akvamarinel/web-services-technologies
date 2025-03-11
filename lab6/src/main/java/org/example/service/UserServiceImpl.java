package org.example.service;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.dao.UserDAO;

import java.util.Base64;

import java.sql.SQLException;


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
            @QueryParam("averageScore") Double averageScale,
            @HeaderParam("Authorization") String auth) {
        try {
            if (!authenticate(auth)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
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
                               @QueryParam("averageScale") double averageScale,
                               @HeaderParam("Authorization") String auth) {
        try {
            if (!authenticate(auth)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
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
    public Response deleteUser(@QueryParam("id") int id,
                               @HeaderParam("Authorization") String auth) {
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
            @QueryParam("id") int id,
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale,
            @HeaderParam("Authorization") String auth
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

    private boolean authenticate(String authHeader) {
        final String USER = "user";
        final String PASSWORD = "password";
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            return false;
        }
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        String credentials = new String(Base64.getDecoder().decode(base64Credentials));
        String[] values = credentials.split(":", 2);
        return values.length == 2 && USER.equals(values[0]) && PASSWORD.equals(values[1]);
    }
}
