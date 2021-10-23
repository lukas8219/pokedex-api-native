package com.lukas8219.io.managers;

import com.lukas8219.io.config.ConfigurationUtils;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerManager {

    private static final HttpServer SERVER;
    private static final Integer SERVER_PORT = Integer.parseInt(ConfigurationUtils.getConfiguration("SERVER_PORT"));
    private static final Logger log = LoggerFactory.getLogger(ServerManager.class);

    static {
        try {
            SERVER = HttpServer.create(new InetSocketAddress("localhost", SERVER_PORT), 0);
            log.info("Server started on port {} successfully", SERVER_PORT);
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when trying to start application");
        }
    }

    static {
        ConnectionManager.createConnection();
    }

    private ServerManager() {
    }

    public static HttpServer retrieveServer() {
        return SERVER;
    }

}
