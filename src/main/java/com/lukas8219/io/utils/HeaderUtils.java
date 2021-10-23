package com.lukas8219.io.utils;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class HeaderUtils {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";

    public static void addDefaultHeaders(Headers headers){
        headers.add(CONTENT_TYPE, APPLICATION_JSON);
    }

    public static void addDefaultHeaders(HttpExchange httpExchange){
        var headers = httpExchange.getResponseHeaders();
        addDefaultHeaders(headers);
    }
}
