package com.lukas8219.io.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lukas8219.io.dao.PokedexDAO;
import com.lukas8219.io.exception.HttpNotFoundException;
import com.lukas8219.io.utils.HeaderUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.lukas8219.io.constants.RequestMethods.GET;

public class PokedexHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if (GET.equals(httpExchange.getRequestMethod())) {
            var response = httpExchange.getResponseBody();
            var pokemonId = parseHttpRequest(httpExchange);
            var pokemon = PokedexDAO.getById(pokemonId);
            var json = new ObjectMapper().writeValueAsString(pokemon); //TODO move objectMapper to util class
            HeaderUtils.addDefaultHeaders(httpExchange);
            httpExchange.sendResponseHeaders(200, json.length());
            response.write(json.getBytes(StandardCharsets.UTF_8));
            response.close();
        }
    }

    public Integer parseHttpRequest(HttpExchange exchange) {
        var query = exchange.
                getRequestURI()
                .toString()
                .split("\\?")[0]
                .split("/");
        if (query.length != 3) {
            throw new HttpNotFoundException();
        }
        return Integer.parseInt(query[2]);
    }

}
