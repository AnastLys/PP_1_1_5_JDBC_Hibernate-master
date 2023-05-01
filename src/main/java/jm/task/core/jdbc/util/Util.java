package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/new_schema1.1.4"; //  useSSL=false" + "&serverTimezone=UTC
    private static final String DB_USERNAME ="root1234";
    private static final String DB_PASSWORD ="root1234";
    private static SessionFactory sessionFactory = null;
    public static Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            //System.out.println("Соединение установлено");
        } catch (ClassNotFoundException | SQLException e){
            System.err.println("Соединение не установлено");
            e.printStackTrace();
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                /*Configuration configuration = new Configuration()
                        .setProperty("hibernate.connection.driver_class", DB_DRIVER)
                        .setProperty("hibernate.connection.url", DB_URL)
                        .setProperty("hibernate.connection.username", DB_USERNAME)
                        .setProperty("hibernate.connection.password", DB_PASSWORD)
                        .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect")
                        .addAnnotatedClass(User.class)
                        .setProperty("hibernate.c3p0.min_size","5")
                        .setProperty("hibernate.c3p0.max_size","200")
                        .setProperty("hibernate.c3p0.max_statements","200");*/
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DB_DRIVER);
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, DB_USERNAME);
                settings.put(Environment.PASS, DB_PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect"); // "org.hibernate.dialect.MySQL5Dialect"
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "");
                settings.put("hibernate.c3p0.min_size","5");
                settings.put("hibernate.c3p0.max_size","200");
                settings.put("hibernate.c3p0.max_statements","200");

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
    public static void closeConnection() {
        if (sessionFactory != null)
            sessionFactory.close();
    }
}
