package com.lomiguk.springapp.repository;

import com.lomiguk.springapp.exception.ConnectionException;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    // Context
    private static final String JAVA_COMP_ENV_JDBC_MY_LOCAL_DB = "java:/comp/env/jdbc/MyLocalDB";
    // DriverManager
    private static final String POSTGRES_URL = "jdbc:postgresql://localhost:5432/JurassicWorld_";
    private static final String USERNAME = "Lomi";
    private static final String PASSWORD = "l0m1";
    private static Connection connection;
    public Connection getContextConnection() throws NamingException {
        try{
            Context context = new InitialContext();
            DataSource dataSource = (DataSource)context.lookup(JAVA_COMP_ENV_JDBC_MY_LOCAL_DB);
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error while getting connection", e);
            throw new ConnectionException(e);
        }
    }

    public Connection getDriverManagerConnection() {
        try{
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.WARNING, "Error while getting Driver class", e);
            throw new ConnectionException(e);
        }
        try {
            return DriverManager.getConnection(POSTGRES_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error while getting connection", e);
            throw new ConnectionException(e);
        }
    }
}
