package jm.task.core.jdbc.util;

import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.Session;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String HOST = "jdbc:mysql://localhost:3306/work";

    private static final SessionFactory sessionFactory;

    static {
        try {
            Configuration conf = new Configuration();
            Properties properties = new Properties();
            properties.put(Environment.DRIVER, DBDRIVER);
            properties.put(Environment.URL, HOST);
            properties.put(Environment.USER, USERNAME);
            properties.put(Environment.PASS, PASSWORD);
            properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            properties.put(Environment.SHOW_SQL, "true");
            properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.put(Environment.HBM2DDL_AUTO, "update");
            conf.setProperties(properties);
            conf.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(conf.getProperties()).build();

            sessionFactory = conf.buildSessionFactory(serviceRegistry);
        } catch (HibernateException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
    public static Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}


//    private static final String URL = "jdbc:mysql://localhost:3306/work";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "root";
//
//    public static Connection getConnection() {
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//            connection.setAutoCommit(false);
//            //System.out.println("Соединение установлено");
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Ошибка соединения");
//        }
//        return connection;

