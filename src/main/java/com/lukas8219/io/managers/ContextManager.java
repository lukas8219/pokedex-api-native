package com.lukas8219.io.managers;

import com.lukas8219.io.filters.CorsFilter;
import com.lukas8219.io.filters.HttpExceptionHandlerFilter;
import com.lukas8219.io.filters.OpenInSessionViewFilter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;

public class ContextManager {

    public static HttpContext createContext(String endpoint, HttpHandler handler){
        var ctx = ServerManager.startServer().createContext(endpoint, handler);
        applyFilters(ctx);
        return ctx;
    }


    private static void applyFilters(HttpContext context){
        context.getFilters().add(new CorsFilter());
        context.getFilters().add(new OpenInSessionViewFilter());
        context.getFilters().add(new HttpExceptionHandlerFilter());
    }
}
