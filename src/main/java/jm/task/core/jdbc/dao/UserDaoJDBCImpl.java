package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String createTable = "CREATE TABLE IF NOT EXISTS `work`.`user` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(45) NULL," +
                "  `lastName` VARCHAR(45) NULL," +
                "  `age` INT NULL," +
                "  PRIMARY KEY (`id`));";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTable);
            System.out.println("Таблица 'user' создана");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при создании таблицы 'user'");
        }
    }

    public void dropUsersTable() {
        String dropUsersTable = "DROP TABLE IF EXISTS work.user;";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(dropUsersTable);
            System.out.println("Таблица 'user' удалена");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Таблицы не существует");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        Connection connection = null;
        try {
            connection = Util.getConnection();
            PreparedStatement statement = connection.prepareStatement("INSERT INTO work.user(name, lastName, age) VALUES (?, ?, ?);");
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println("Пользователь " + name + " добавлен");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении пользователя");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeUserById(long id) {
        String removeUserById = "DELETE FROM work.user WHERE id = " + id + ";";
        Connection connection = null;
        try {
            connection = Util.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(removeUserById);
            System.out.println("Пользователь c id = " + id + " удалён");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Ошибка при удалении пользователя");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        String getAllUsers = "SELECT * FROM work.user";
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(getAllUsers);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка получении ResultSet");
        }
        return users;
    }

    public void cleanUsersTable() {
        String cleanUsersTable = "DELETE FROM work.user;";
        try (Connection connection = Util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(cleanUsersTable);
            System.out.println("Таблица 'user' очищена");
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка при очистке таблицы");
        }
    }
}
