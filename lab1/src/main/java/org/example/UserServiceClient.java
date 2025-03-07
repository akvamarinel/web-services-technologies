package org.example;

import jakarta.xml.ws.Service;
import org.example.model.User;
import org.example.service.UserService;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

public class UserServiceClient {

    public static void main(String[] args) {
        try {

            URL url = new URL("http://localhost:8080/lab1/ws/userService?wsdl");


            QName qname = new QName("http://service.example.org/", "UserServiceImplService");
            Service service = Service.create(url, qname);

            UserService userService = service.getPort(UserService.class);

            List<User> users = userService.searchUsers("mashusik", null, null, null);
            users.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
