package ch.rootkit.varoke.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;

import com.mysql.jdbc.StringUtils;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.web.cookie.Cookie;
import ch.rootkit.varoke.web.handlers.*;
public class RequestHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange he) throws IOException {
    	OutputStream output = he.getResponseBody();
    	HttpCookie cookie = null;
    	Cookie cookie2 = null;
    	String cookieString = he.getRequestHeaders().getFirst("Cookie");
    	boolean newSession = false;
    	if(cookieString != null){
    		cookie = HttpCookie.parse(cookieString).get(0);
    		cookie2 = Varoke.getWeb().getCookies().get(cookie.getValue());
    	}
    	if(cookie2 == null){
    		cookie2 = Varoke.getWeb().getCookies().createCookie();
    		cookie = new HttpCookie("varoke.session.id", cookie2.getIdentifier());
    		newSession = true;
    	}
    	if(newSession){
    		he.getResponseHeaders().set("Set-Cookie", cookie.toString());
    	}
    	try{
	    	if(he.getRequestURI().toString().contains(".")){
	    		File f = Paths.get(System.getProperty("user.dir") + "/web",he.getRequestURI().toString()).toFile();
	    		he.sendResponseHeaders(200, f.length());
	    		he.getResponseHeaders().set("Content-Type", Files.probeContentType(f.toPath()));
	    		Files.copy(f.toPath(), output);
	    	}else{
	    		byte[] buffer = getHandler(he.getRequestURI().getPath().substring(1)).handle(he,  parseArguments(he), cookie2);
	    		he.getResponseHeaders().set("Content-Type","text/html");
	    		he.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
	    		output.write(buffer);
	    	}
    	}catch(NoSuchFileException nosuchfile){
    		output.write(new ErrorHandler().handle(he,  parseArguments(he), cookie2));
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
		output.close();
    }
    
    public WebHandler getHandler(String request){
    	switch(request){
    	case "":
    		return new IndexHandler();
    	case "login":
    		return new LoginHandler();
    	case "me":
    		return new MeHandler();
    	case "hotel":
    		return new HotelHandler();
    	case "register":
    		return new RegisterHandler();
    	default:
    		return new ErrorHandler();
    	}
    }
    public HashMap<String, HashMap<String, String>> parseArguments(HttpExchange he) throws IOException{
    	HashMap<String, HashMap<String, String>> result = new HashMap<String, HashMap<String, String>>();
    	result.put("GET", new HashMap<String, String>());
    	result.put("POST", new HashMap<String, String>());
    	//PARSING GET
    	parseQuery(he.getRequestURI().getQuery(), result.get("GET"));
    	
    	//PARSING POST
    	InputStreamReader isr = new InputStreamReader(he.getRequestBody(),Varoke.getCharset());
        BufferedReader br = new BufferedReader(isr);
        parseQuery(br.readLine(), result.get("POST"));
        br.close();
        isr.close();
    	return result;
    }
    
	@SuppressWarnings("deprecation")
	private void parseQuery(String query, HashMap<String, String> map){
    	if(query == null)
    		return;
    	if(query.contains("&")){
    		for (String param : query.split("&")) {
                String pair[] = param.split("=");
                if (pair.length > 1) {
                	map.put(URLDecoder.decode(pair[0]), URLDecoder.decode(pair[1]));
                }else{
                	map.put(URLDecoder.decode(pair[0]), "");
                }
            }
    	}else{
    		if(!StringUtils.isNullOrEmpty(query)){
    			query = query.replace("?", "");
    			String pair[] = query.split("=");
    			if(pair.length > 1){
    				map.put(URLDecoder.decode(pair[0]), URLDecoder.decode(pair[1]));
    			}else{
    				map.put(URLDecoder.decode(pair[0]), "");
    			}
    		}
    	}
    }
} 