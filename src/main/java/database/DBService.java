package database;

import database.dao.UsersDAO;
import database.datasets.UsersDataSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import org.hibernate.service.ServiceRegistry;

public class DBService {

    private static final String hibernate_dialect = "org.hibernate.dialect.PostgreSQL9Dialect";
    private static final String hibernate_connection_driver_class = "org.postgresql.Driver";
    private static final String hibernate_connection_url = "jdbc:postgresql://127.0.0.1:5432/webServer";
    private static final String hibernate_connection_username = "testAdmin";
    private static final String hibernate_connection_password = "qwerty";
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "validate";

    private final SessionFactory sessionFactory;

    public DBService(){
        Configuration configuration = getConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    public UsersDataSet getUserById(long id) {
        Session session = sessionFactory.openSession();
        UsersDAO usersDAO = new UsersDAO(session);
        UsersDataSet dataSet = usersDAO.get(id);
        session.close();

        return dataSet;
    }

    public UsersDataSet getUserByName(String name) {
        Session session = sessionFactory.openSession();
        UsersDAO usersDAO = new UsersDAO(session);
        UsersDataSet dataSet = usersDAO.getUserByName(name) ;
        session.close();

        return dataSet;
    }

    public long addUser(String name, String password){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        UsersDAO usersDAO = new UsersDAO(session);
        long id = usersDAO.insertUser(name, password);
        transaction.commit();
        session.close();

        return id;
    }

    public void printConnectionInfo(){
            Session session = sessionFactory.openSession();
            session.doWork(connection -> {
                System.out.println("DB name: " + connection.getMetaData().getDatabaseProductName());
                System.out.println("DB version: " + connection.getMetaData().getDatabaseProductVersion());
                System.out.println("Driver: " + connection.getMetaData().getDriverName());
                System.out.println("Autocommit: " + connection.getAutoCommit());
            });
    }

    private Configuration getConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(UsersDataSet.class);

        configuration.setProperty("hibernate.dialect", hibernate_dialect);
        configuration.setProperty("hibernate.connection.driver_class", hibernate_connection_driver_class);
        configuration.setProperty("hibernate.connection.url", hibernate_connection_url);
        configuration.setProperty("hibernate.connection.username", hibernate_connection_username);
        configuration.setProperty("hibernate.connection.password", hibernate_connection_password);
        configuration.setProperty("hibernate.show.sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        configuration.setProperty("javax.persistence.jdbc.driver", "org.postgresql.Driver");

        return configuration;
    }

    private SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
