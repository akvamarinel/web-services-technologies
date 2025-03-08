package org.example.service;

import jakarta.ws.rs.QueryParam;
import org.example.model.User;

import jakarta.ws.rs.GET;
import java.util.List;

public interface UserService {

    @GET
    List<User> searchUsers(
            @QueryParam("name") String name,
            @QueryParam("age") Integer age,
            @QueryParam("city") String city,
            @QueryParam("averageScore") Double averageScore
    );
}
