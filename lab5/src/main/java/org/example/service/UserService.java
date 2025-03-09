package org.example.service;

import jakarta.ws.rs.*;
import org.example.model.User;

import java.util.List;


public interface UserService {

    @GET
    List<User> searchUsers(
            @QueryParam("name") String name,
            @QueryParam("age") Integer age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") Double averageScale
    );

    @POST
    int createUser(
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale);

    @DELETE
    boolean deleteUser(
            @QueryParam("id") int id);

    @PUT
    boolean updateUser(
            @QueryParam("int") int id,
            @QueryParam("name") String name,
            @QueryParam("age") int age,
            @QueryParam("city") String city,
            @QueryParam("averageScale") double averageScale);

}
