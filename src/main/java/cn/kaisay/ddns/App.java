package cn.kaisay.ddns;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

/**
 * Hello world!
 *
 */
public class App 
{
    private final static  String XFO_STRING = "X-Forwarded-For";
    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080,"0.0.0.0") 
                .setHandler(new HttpHandler() { 

                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        exchange.getRequestHeaders().getHeaderNames().forEach(name -> System.out.println("name is : "+name));
                        System.out.println("x-f: "+ exchange.getRequestHeaders().getFirst(XFO_STRING));
                        System.out.println("x-f checkpoint : "+ exchange.getRequestHeaders().getHeaderNames().contains(XFO_STRING) );
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World! You are requesting from "
                        + (exchange.getRequestHeaders().getHeaderNames().contains(new HttpString(XFO_STRING)) ? exchange.getRequestHeaders().getFirst(XFO_STRING) : exchange.getSourceAddress()));
                        exchange.getRequestHeaders().forEach(header -> System.out.println(header.getHeaderName()+" : "+header.toString()));
                        
                    }
                    
                })
                .build();
        server.start();
    }
}
