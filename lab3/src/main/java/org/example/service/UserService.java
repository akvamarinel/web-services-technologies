package org.example.service;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.example.exception.UserServiceException;
import org.example.model.User;

import java.util.List;

@WebService
public interface UserService {

    @WebMethod
    List<User> searchUsers(
            @WebParam(name = "name") String name,
            @WebParam(name = "age") Integer age,
            @WebParam(name = "city") String city,
            @WebParam(name = "averageScale") Double averageScale
    ) throws UserServiceException;

    @WebMethod
    int createUser(String name, int age, String city, Double averageScale) throws UserServiceException;

    @WebMethod
    boolean deleteUser(int id) throws UserServiceException;

    @WebMethod
    boolean updateUser(int id, String name, int age, String city, Double averageScale) throws UserServiceException;

}
