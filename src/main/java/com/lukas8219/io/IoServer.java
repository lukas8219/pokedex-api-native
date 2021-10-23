package com.lukas8219.io;

import com.lukas8219.io.filters.CorsFilter;
import com.lukas8219.io.filters.ExceptionHandlerFilter;
import com.lukas8219.io.filters.OpenInSessionViewFilter;
import com.lukas8219.io.handlers.EndpointHandler;
import com.sun.net.httpserver.HttpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;

public class IoServer {

    private static final Logger log = LoggerFactory.getLogger(IoServer.class);

    public static void main(String[] args) {
        try {
            var server = HttpServer.create(new InetSocketAddress("localhost", 8080), 0);
            var context = server.createContext("/pokedex", new EndpointHandler());
            context.getFilters().add(new CorsFilter());
            context.getFilters().add(new OpenInSessionViewFilter());
            context.getFilters().add(new ExceptionHandlerFilter());
            server.start();
            log.info("Server started successfully!");
        } catch (IOException e) {
            log.error("Error when trying to open socket : ", e);
        }
    }
}
