package cn.kaisay.ddns;

import java.util.HashMap;

import com.google.gson.Gson;

import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

public class IpJsonHandler implements HttpHandler{

    private static final String XFO_STRING = "X-Forwarded-For";

    @Override
    public void handleRequest(HttpServerExchange exchange) throws Exception {

        HashMap<String, String> response = new HashMap<String, String>();
        if (exchange.getRequestHeaders().getHeaderNames().contains(new HttpString(XFO_STRING))) {
            response.put("x-forward", Boolean.TRUE.toString());
            response.put("sourceIP", exchange.getRequestHeaders().getFirst(XFO_STRING));
        } else {
            response.put("x-forward", Boolean.FALSE.toString());
            response.put("sourceIP", exchange.getSourceAddress().toString());
        }
        // exchange.getRequestHeaders().getHeaderNames().forEach(name ->
        // System.out.println("name is : "+name));
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, " application/json");
        exchange.getResponseSender().send(new Gson().toJson(response));
    }

    
}
