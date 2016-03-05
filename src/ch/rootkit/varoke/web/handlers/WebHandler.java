package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.web.cookie.Cookie;

public interface WebHandler {

	public abstract byte[] handle(HttpExchange he, HashMap<String, HashMap<String,String>> arguments, Cookie cookie);
}
