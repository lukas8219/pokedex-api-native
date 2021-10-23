package com.lukas8219.io.managers;

import com.lukas8219.io.config.ConfigurationUtils;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerManager {

    private static final HttpServer SERVER;
    private static final Integer SERVER_PORT = Integer.parseInt(ConfigurationUtils.getConfiguration("SERVER_PORT"));
    static {
        try {
            SERVER = HttpServer.create(new InetSocketAddress("localhost", SERVER_PORT), 0);
            //TODO externalize port
        } catch (IOException e) {
            throw new RuntimeException("An error occurred when trying to start application");
        }
    }

    static {
        ConnectionManager.createConnection();
    }

    private ServerManager(){}

    public static HttpServer getServer(){
        return SERVER;
    }

}
