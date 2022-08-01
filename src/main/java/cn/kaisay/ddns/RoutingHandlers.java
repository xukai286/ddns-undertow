package cn.kaisay.ddns;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.util.Headers;

import static io.undertow.Handlers.resource;

public class RoutingHandlers {

    public static HttpHandler welcome() {
        System.out.println("  !  welcome !");

        HttpHandler index = resource(new ClassPathResourceManager(
            RoutingHandlers.class.getClassLoader()))
        .addWelcomeFiles("public/index.html");

        // System.out.println("  !  welcome !");
        return index;

    }

    public static HttpHandler plainTextHandler(String value) {

        return new PlainTextHandler(value);
    }

    public static HttpHandler IpJsonHandler() {
        return new IpJsonHandler();
    }

    public static void notFoundHandler(HttpServerExchange exchange) {
        exchange.setStatusCode(404);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
        exchange.getResponseSender().send("Love Q !");
    }
}
