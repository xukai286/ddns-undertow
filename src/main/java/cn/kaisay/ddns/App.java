package cn.kaisay.ddns;

import static io.undertow.Handlers.path;
import static io.undertow.Handlers.resource;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.BlockingHandler;
import io.undertow.server.handlers.resource.ClassPathResourceManager;

/**
 * DDNS Server
 *
 */
public class App {
    public static void main(final String[] args) {
/*         DNSHanlder dns = new DNSHanlder();
        dns.authenticate(); */
        Undertow server = Undertow.builder()
                .addHttpListener(8080, "0.0.0.0")
                .setHandler(path()
                        // REST API path
                        .addPrefixPath("/api", Handlers.routing()
                                .get("/ip", new BlockingHandler(RoutingHandlers.IpJsonHandler()))
                                // .delete("/customers/{customerId}", RoutingHandlers.IpJsonHandler())
                                .setFallbackHandler(RoutingHandlers::notFoundHandler))

                        // Redirect root path to /static to serve the index.html by default
                        // .addExactPath("/", Handlers.redirect("/static"))

                        // Serve all static files from a folder
                        .addPrefixPath("/",
                                resource(new ClassPathResourceManager(
                                        App.class.getClassLoader()))
                                        .addWelcomeFiles("public/index.html"))

                )

                .build();
        server.start(); 
    }
}
