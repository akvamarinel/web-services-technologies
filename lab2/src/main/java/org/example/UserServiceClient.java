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

            System.out.println("search users by filters");
            List<User> users = userService.searchUsers("mashusik", null, null, null);
            users.forEach(System.out::println);

            System.out.println("create new user");
            int newUserId = userService.createUser("Maria Karaseva", 18, "moscow", 5.0);
            List<User> newUsers = userService.searchUsers("Maria Karaseva", null, null, null);
            var newUser = newUsers.stream().filter(user -> user.getName().equals("Maria Karaseva")).findFirst();
            if (newUser.isPresent()) {
                System.out.println("New User has been created with id " + newUserId);
            } else {
                System.out.println("New User has not been created");
            }

            System.out.println("update user by id (age)");
            boolean res = userService.updateUser(newUserId, "Maria Karaseva", 20, "moscow", 5.0);
            newUsers = userService.searchUsers("Maria Karaseva", null, null, null);
            if (res && newUsers.stream().anyMatch(user -> user.getName().equals("Maria Karaseva"))) {
                if (newUsers.get(0).getAge() == 20) {
                    System.out.println("New User has been updated");
                } else {
                    System.out.println("New User has not been updated");
                }
            } else {
                System.out.println("New User has not been updated");
            }

            System.out.println("delete user by id (age)");
            boolean resDelete = userService.deleteUser(newUserId);
            if (resDelete) {
                newUsers = userService.searchUsers("Maria Karaseva", null, null, null);
                if (newUsers.stream().noneMatch(user -> user.getName().equals("Maria Karaseva"))) {
                    System.out.println("User has been deleted");
                } else {
                    newUsers.forEach(System.out::println);
                    System.out.println("User has not been deleted");
                }
            } else {
                System.out.println("User has been deleted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
