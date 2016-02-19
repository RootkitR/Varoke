package ch.rootkit.varoke.api.handlers;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
    	final String hello = "Welcome to Varoke <3";
            he.sendResponseHeaders(200, hello.length());
            OutputStream os = he.getResponseBody();
            os.write(hello.getBytes());
            os.close();
    }
}