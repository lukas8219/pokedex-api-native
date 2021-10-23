package com.lukas8219.io.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukas8219.io.dao.PokedexDAO;
import com.lukas8219.io.utils.HeaderUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static com.lukas8219.io.constants.RequestMethods.GET;

public class EndpointHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (GET.equals(httpExchange.getRequestMethod())) {
            var pokemonId = parseHttpRequest(httpExchange);
            var outputStream = httpExchange.getResponseBody();
            var pokemon = PokedexDAO.getById(pokemonId);
            var json = new ObjectMapper().writeValueAsString(pokemon);
            HeaderUtils.addDefaultHeaders(httpExchange);
            httpExchange.sendResponseHeaders(200, json.length());
            outputStream.write(json.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        }
    }

    public Integer parseHttpRequest(HttpExchange exchange) {
        var query = exchange.
                getRequestURI()
                .toString()
                .split("\\?")[0]
                .replaceAll("[^0-9+]", "");
        return Integer.parseInt(query);
    }

}
