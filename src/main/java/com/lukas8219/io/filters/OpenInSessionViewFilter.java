package com.lukas8219.io.filters;

import com.lukas8219.io.jdbc.ConnectionManager;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

final public class OpenInSessionViewFilter extends Filter {

    private static final Logger log = LoggerFactory.getLogger(OpenInSessionViewFilter.class);

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        try (var connection = ConnectionManager.getConnection()) {
            chain.doFilter(httpExchange);
        } catch (Exception e) {
            log.debug("An exception occurred. Rolling back transaction", e);
            rollBackTransaction();
        } finally {
            log.debug("Closing Database connection!");
            closeConnection();
        }
    }

    private void rollBackTransaction() {
        try {
            ConnectionManager.getConnection()
                    .rollback();
        } catch (SQLException ex) {
            log.error("An error occurred when trying to rollback", ex);
        }
    }

    private void closeConnection() {
        try {
            ConnectionManager.getConnection()
                    .close();
        } catch (SQLException e) {
            log.debug("An error occurred when trying to close connection", e);
        }
    }

    @Override
    public String description() {
        return "Class to implement OpenInSessionView";
    }
}
