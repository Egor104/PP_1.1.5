package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.sql.SQLException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private final Session session = Util.getSession();

    @Override
    public void createUsersTable() {
        session.beginTransaction();
        session.createSQLQuery(" CREATE TABLE IF NOT EXISTS User (" +
                "  id INT NOT NULL AUTO_INCREMENT," +
                "  name VARCHAR(45) NULL," +
                "  lastName VARCHAR(45) NULL," +
                "  age INT NULL," +
                "  PRIMARY KEY (id));").addEntity(User.class).executeUpdate();
        System.out.println("Таблица 'user' создана");
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS work.user;").executeUpdate();
        System.out.println("Таблица 'user' удалена");
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            //session = Util.getSession();
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
            session.close();
            System.out.println("Пользователь " + name + " добавлен");
        } catch (HibernateException e) {
            System.out.println("Ошибка при добавлении пользователя");
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            //session = Util.getSession();
            session.beginTransaction();
            session.createQuery("DELETE User WHERE id = id").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Пользователь c id = " + id + " удалён");
        } catch (HibernateException e) {
            System.out.println("Ошибка при удалении пользователя");
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        //session = Util.getSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            //session = Util.getSession();
            session.beginTransaction();
            session.createQuery("DELETE User").executeUpdate();
            session.getTransaction().commit();
            session.close();
            System.out.println("Таблица 'user' очищена");
        } catch (HibernateException e) {
            System.out.println("Ошибка при очистке таблицы");
            e.printStackTrace();
            session.getTransaction().rollback();
        }
    }
}
