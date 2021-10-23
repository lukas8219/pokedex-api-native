package com.lukas8219.io.managers;

import com.lukas8219.io.config.ConfigurationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private final static Logger log = LoggerFactory.getLogger(ConnectionManager.class);
    private final static String DATABASE_URL = ConfigurationUtils.getConfiguration("DATABASE_URL");
    private final static String DATABASE_USERNAME = ConfigurationUtils.getConfiguration("DATABASE_USERNAME");
    private final static String DATABASE_PASSWORD = ConfigurationUtils.getConfiguration("DATABASE_PASSWORD");

    private static Connection CONNECTION;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            log.error("An error occurred when trying to load Driver!");
        }
    }

    public static Connection getConnection() {
        if (CONNECTION == null || connectionIsClosed()) {
            createConnection();
        }
        return CONNECTION;
    }

    private static boolean connectionIsClosed() {
        try {
            return CONNECTION != null && CONNECTION.isClosed();
        } catch (SQLException e) {
            throw new RuntimeException("An error occurred when trying to check if connection is closed", e);
        }
    }

    public static void createConnection() {
        try {
            CONNECTION = DriverManager.getConnection(DATABASE_URL,
                    DATABASE_USERNAME,
                    DATABASE_PASSWORD);
            log.debug("Connection successfully created");
        } catch (SQLException e) {
            throw new RuntimeException("Unable to connect to Database", e);
        }
    }

}
