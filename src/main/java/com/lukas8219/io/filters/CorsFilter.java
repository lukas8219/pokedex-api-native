package com.lukas8219.io.filters;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

final public class CorsFilter extends Filter {

    private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);
    private final String USER_HEADER = "user";

    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        var host = httpExchange.getRemoteAddress().getAddress().getHostAddress();
        if (!validHosts().contains(host) && !isValidHeader(httpExchange)) {
            log.warn("Incoming request from {}", host);
            httpExchange.sendResponseHeaders(400, "Error".length());
        }
        chain.doFilter(httpExchange);
    }

    private boolean isValidHeader(HttpExchange httpExchange) {
        var headers = httpExchange.getRequestHeaders().getFirst(USER_HEADER);
        return headers != null && validUsers().contains(headers);
    }

    private List<String> validUsers() {
        return List.of("lucas", "gabriel");
    }

    private List<String> validHosts() {
        return List.of("127.0.0.1", "localhost:3000");
    }

    @Override
    public String description() {
        return "Security filter: Check the remote address request";
    }
}
