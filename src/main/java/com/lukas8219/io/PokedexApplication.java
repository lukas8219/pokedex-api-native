package com.lukas8219.io;

import com.lukas8219.io.handlers.PokedexHandler;
import com.lukas8219.io.managers.ContextManager;
import com.lukas8219.io.managers.ServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PokedexApplication {

    private static final Logger log = LoggerFactory.getLogger(PokedexApplication.class);

    public static void main(String[] args) {
        //TODO add function run all scripts and populate DB.
        var server = ServerManager.getServer();
        ContextManager.createContext("/pokedex", new PokedexHandler());
        server.start();
        log.info("Application started successfully!");
    }
}
