package com.lukas8219.io;

import com.lukas8219.io.handlers.PokedexHandler;
import com.lukas8219.io.managers.ContextManager;
import com.lukas8219.io.managers.ServerManager;

public class PokedexApplication {

    public static void main(String[] args) {
        //TODO add function run all scripts and populate DB.
        var server = ServerManager.retrieveServer();
        ContextManager.createContext("/pokedex", new PokedexHandler());
        server.start();
    }
}
