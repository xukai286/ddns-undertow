package cn.kaisay.ddns;

import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
                .addHttpListener(8080,"0.0.0.0") 
                .setHandler(new HttpHandler() { 

                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        // TODO Auto-generated method stub
                        exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "text/plain");
                        exchange.getResponseSender().send("Hello World!");
                        exchange.getRequestHeaders().forEach(header -> System.out.println(header.toString()));
                        
                    }
                    
                })
                .build();
        server.start();
    }
}
