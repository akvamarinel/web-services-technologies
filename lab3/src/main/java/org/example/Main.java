package org.example;
import jakarta.xml.ws.Endpoint;
import org.example.service.UserServiceImpl;

public class Main {

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8080/lab1/ws/userService", new UserServiceImpl());
        System.out.println("UserService is running at http://localhost:8080/userService");
    }
}