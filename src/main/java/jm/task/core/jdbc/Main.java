package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final UserServiceImpl userService = new UserServiceImpl();

    public static void main(String[] args) {
        userService.createUsersTable();
        userService.saveUser("Denis", "Denisov", (byte) 33);
        userService.saveUser("Egor", "Egorov", (byte) 24);
        userService.saveUser("Ivan", "Ivanov", (byte) 55);
        userService.saveUser("Petr", "Petrov", (byte) 44);
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
