package org.example;

import org.example.model.User;
import org.example.service.UserService;

import javax.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.URL;
import java.util.List;

public class UserServiceClient {

    public static void main(String[] args) {
        try {

            URL url = new URL("http://localhost:8080/userService?wsdl");


            QName qname = new QName("http://service/", "UserService");
            Service service = Service.create(url, qname);

            UserService userService = service.getPort(UserService.class);

            List<User> users = userService.searchUsers("mashusik", null, null, null);
            users.forEach(System.out::println);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
