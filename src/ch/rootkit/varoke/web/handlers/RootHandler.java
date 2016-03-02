package ch.rootkit.varoke.web.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ch.rootkit.varoke.Varoke;

public class RootHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
    	final String hello = "Varoke Emulator (Current Version : " +  Varoke.Version;
            he.getResponseHeaders().set("Content-Type", "text/html");
            he.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            OutputStream os = he.getResponseBody();
            os.write(hello.getBytes());
            os.close();
    }
}