package org.example.service;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.example.model.User;

import java.util.List;


public interface UserService {

    @GET
    Response searchUsers(
            @QueryParam("name") String name,
            @QueryParam("age") Integer age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") Double averageScale
    );

    @POST
    Response createUser(
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale);

    @DELETE
    Response deleteUser(
            @QueryParam("id") int id);

    @PUT
    Response updateUser(
            @QueryParam("int") int id,
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale);

}
