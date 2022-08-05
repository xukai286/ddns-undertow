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

        String ip = null;
        HashMap<String, String> response = new HashMap<String, String>();
        if (exchange.getRequestHeaders().getHeaderNames().contains(new HttpString(XFO_STRING))) {
            response.put("x-forward", Boolean.TRUE.toString());
            ip = exchange.getRequestHeaders().getFirst(XFO_STRING);
            response.put("sourceIP", ip);
        } else {
            response.put("x-forward", Boolean.FALSE.toString());
            ip = exchange.getSourceAddress().getAddress().getHostAddress();
            response.put("sourceIP", ip);
        }
        // exchange.getRequestHeaders().getHeaderNames().forEach(name ->
        // System.out.println("name is : "+name));
        new DNSHanlder().authenticate(ip);
        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, " application/json");
        exchange.getResponseSender().send(new Gson().toJson(response));
    }

    
}
