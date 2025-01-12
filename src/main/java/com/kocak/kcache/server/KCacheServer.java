package com.kocak.kcache.server;

import com.kocak.kcache.config.KCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Component
public class KCacheServer {

    @Autowired
    private KCacheConfig config;

    public void startServer() {
        try {
            int port = config.getPort();
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext("/kcache", httpExchange -> {
                String response = "K-CACHE Server is running on port " + port;
                httpExchange.sendResponseHeaders(200, response.length());
                httpExchange.getResponseBody().write(response.getBytes());
                httpExchange.getResponseBody().close();
            });
            server.start();
            System.out.println("K-CACHE Server started at port: " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
