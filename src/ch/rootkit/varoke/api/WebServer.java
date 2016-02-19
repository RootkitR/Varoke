package ch.rootkit.varoke.api;

import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import ch.rootkit.varoke.api.handlers.RootHandler;
import ch.rootkit.varoke.utils.Configuration;
import ch.rootkit.varoke.utils.Logger;

public class WebServer {

	public static void start() throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(Configuration.get("http.ip"), Integer.parseInt(Configuration.get("http.port"))), 0);
		server.createContext("/", new RootHandler());
		server.setExecutor(null);
		server.start();
		Logger.printVarokeLine("Listening for Web Requests on " +  Configuration.get("http.ip") + ":" + Configuration.get("http.port"));
	}
}
