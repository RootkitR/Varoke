package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.web.cookie.Cookie;

public class ErrorHandler implements WebHandler {

	@Override
	public byte[] handle(HttpExchange he, HashMap<String, HashMap<String,String>> arguments, Cookie cookie) {
		byte[] result = "<h1>Page not found!</h1>".getBytes();
		return result;
	}

}
