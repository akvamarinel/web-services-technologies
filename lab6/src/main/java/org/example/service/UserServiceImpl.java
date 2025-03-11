package org.example.service;


import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.dao.UserDAO;
import org.example.exception.ThrottlingException;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;


@Path("/users")
@Consumes("application/json")
@Produces("application/json")
public class UserServiceImpl implements UserService {

    private static final AtomicInteger COUNTER = new AtomicInteger();
    private static final int CONCURRENCY = 3;

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
            if (COUNTER.incrementAndGet() > CONCURRENCY) {
                COUNTER.decrementAndGet();
                throw new ThrottlingException("Too many requests. Please try again later.");
            }
            return Response.status(Response.Status.OK).entity(userDAO.findUsers(name, age, city, averageScale)).build();
        } catch (SQLException | ThrottlingException e) {
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
            if (COUNTER.incrementAndGet() > CONCURRENCY) {
                COUNTER.decrementAndGet();
                throw new ThrottlingException("Too many requests. Please try again later.");
            }
            int res = userDAO.createUser(name, age, city, averageScale);
            if (res > 0) {
                return Response.status(Response.Status.CREATED).entity(res).build();
            }
            return Response.serverError().build();
        } catch (SQLException | ThrottlingException e) {
            return Response.serverError().build();
        }
    }

    @Override
    @DELETE
    public Response deleteUser(@QueryParam("id") int id) {
        try {
            if (COUNTER.incrementAndGet() > CONCURRENCY) {
                COUNTER.decrementAndGet();
                throw new ThrottlingException("Too many requests. Please try again later.");
            }
            boolean res = userDAO.deleteUser(id);
            if (res) {
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        } catch (SQLException | ThrottlingException e) {
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
            if (COUNTER.incrementAndGet() > CONCURRENCY) {
                COUNTER.decrementAndGet();
                throw new ThrottlingException("Too many requests. Please try again later.");
            }
            boolean res = userDAO.updateUser(id, name, age, city, averageScale);
            if (res) {
                return Response.status(Response.Status.OK).entity(true).build();
            }
            return Response.serverError().build();
        } catch (SQLException | ThrottlingException e) {
            return Response.serverError().build();
        }
    }

}
