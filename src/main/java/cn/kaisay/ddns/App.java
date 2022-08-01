package cn.kaisay.ddns;

import java.util.HashMap;

import com.google.gson.Gson;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.RoutingHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;
import io.undertow.util.Headers;
import io.undertow.util.HttpString;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;
/**
 * DDNS Server 
 *
 */
public class App 
{
    private final static  String XFO_STRING = "X-Forwarded-For";
    public static void main(final String[] args) {
        Undertow server = Undertow.builder()
/* 
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(path().addPrefixPath("/",
                        resource(new ClassPathResourceManager(
                                App.class.getClassLoader()))
                                .addWelcomeFiles("public/index.html"))) */

/*                 .addHttpListener(8080, "0.0.0.0", 
                
                    new RoutingHandler()
                            .get("/", RoutingHandlers.welcome())
                            .get("/json", RoutingHandlers.IpJsonHandler())
                            .setFallbackHandler(
                                path().addPrefixPath("/",
                                    resource(new ClassPathResourceManager(
                                            App.class.getClassLoader()))
                                            .addWelcomeFiles("public/index.html"))
                            )
                            
                ) */
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(path()
                            // REST API path
                            .addPrefixPath("/api", Handlers.routing()
                            .get("/ip",RoutingHandlers.IpJsonHandler())
                            // .delete("/customers/{customerId}", RoutingHandlers.IpJsonHandler())
                            .setFallbackHandler(RoutingHandlers::notFoundHandler))

                        // Redirect root path to /static to serve the index.html by default
                        .addExactPath("/", Handlers.redirect("/static"))

                        // Serve all static files from a folder
                        .addPrefixPath("/static", 
                            resource(new ClassPathResourceManager(
                                            App.class.getClassLoader()))
                                            .addWelcomeFiles("public/index.html"))
                        
                        )
                
                
                .build();
        server.start();
    }
}
