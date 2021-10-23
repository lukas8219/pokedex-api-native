package com.lukas8219.io.filters;

import com.lukas8219.io.exception.HttpException;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HttpExceptionHandlerFilter extends Filter {
    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {
        try {
            chain.doFilter(httpExchange);
        } catch (HttpException e){
            httpExchange.sendResponseHeaders(e.getCODE(), e.getBody().length);
            httpExchange.getResponseBody().write(e.getBody());
            httpExchange.getResponseBody().close();
        }
    }

    @Override
    public String description() {
        return null;
    }
}
