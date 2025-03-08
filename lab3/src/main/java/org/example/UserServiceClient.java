package org.example;

import jakarta.xml.ws.Service;
import org.example.exception.UserServiceException;
import org.example.service.UserService;

import javax.xml.namespace.QName;
import java.net.URL;

public class UserServiceClient {

    public static void main(String[] args) throws Exception {


        URL url = new URL("http://localhost:8080/lab1/ws/userService?wsdl");

        QName qname = new QName("http://service.example.org/", "UserServiceImplService");
        Service service = Service.create(url, qname);

        UserService userService = service.getPort(UserService.class);

        System.out.println("create user");
        int userId = -1;
        try {
            userId = userService.createUser("Maria Karaseva", 23, "spb", 5.0);
        } catch (UserServiceException e) {
            System.out.println(e.getMessage());
        }

        userId = userId + 1000;
        System.out.println("invalid try to delete user with id " + userId);
        try {
            userService.deleteUser(userId);
        } catch (UserServiceException e) {
            System.out.println(e.getServiceFault().getMessage());
        }
    }
}
