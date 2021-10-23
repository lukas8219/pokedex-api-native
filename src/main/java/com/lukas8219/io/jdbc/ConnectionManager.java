package com.lukas8219.io.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/pokedex";
    private final static String DATABASE_USERNAME = "root";
    private final static String DATABASE_PASSWORD = "12345678@";
    private final static Logger log = LoggerFactory.getLogger(ConnectionManager.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("An error occurred when trying to load Driver!");
        }
    }

    private static Connection CONNECTION;

    public static Connection getConnection() {
        if (CONNECTION == null || connectionIsClosed()) {
            createConnection();
        }
        return CONNECTION;
    }

    private static boolean connectionIsClosed() {
        try {
            return CONNECTION != null && CONNECTION.isClosed();
        } catch (SQLException e){
            throw new RuntimeException("An error occurred when trying to check if connection is closed", e);
        }
    }

    public static void createConnection(){
        try {
            CONNECTION = DriverManager.getConnection(DATABASE_URL,
                    DATABASE_USERNAME,
                    DATABASE_PASSWORD);

            log.debug("Connection successfully created");
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred when trying to stablish a SQL connection", e);
        }
    }

}
