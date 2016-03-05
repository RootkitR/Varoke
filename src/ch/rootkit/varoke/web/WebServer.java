package ch.rootkit.varoke.web;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.lang3.StringEscapeUtils;

import com.sun.net.httpserver.HttpServer;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Configuration;
import ch.rootkit.varoke.utils.Logger;
import ch.rootkit.varoke.web.cookie.CookieManager;
import ch.rootkit.varoke.web.habbohotel.users.UserManager;

public class WebServer {

	private CookieManager cookieManager;
	private UserManager userManager;
	public void start() throws Exception {
		cookieManager = new CookieManager();
		userManager = new UserManager();
		HttpServer server = HttpServer.create(new InetSocketAddress(Configuration.get("http.ip"), Integer.parseInt(Configuration.get("http.port"))), 0);
		server.createContext("/", new RequestHandler());
        server.setExecutor(null);
		server.start();
		Logger.printVarokeLine("Listening for Web Requests on " +  Configuration.get("http.ip") + ":" + Configuration.get("http.port"));
	}
	
	public CookieManager getCookies(){
		return cookieManager;
	}
	
	public byte[] getAsset(String filename){
		try {
			return Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/web", filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "404".getBytes();
	}
	
	public UserManager getUserManager(){
		return userManager;
	}
	
	public String sha256(String plain){  
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plain.getBytes());
			return new sun.misc.BASE64Encoder().encode(md.digest());
	    } catch (NoSuchAlgorithmException e) {/*VERY BAD*/}
	    return "";
	}
	
	public String md5(String plain){
		try {
	        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
	        byte[] array = md.digest(plain.getBytes());
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < array.length; ++i) {
	          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
	       }
	        return sb.toString();
	    } catch (java.security.NoSuchAlgorithmException e) {
	    }
	    return "";
	}
	
	public String createSSO(String username){
		return username + "-" + md5(Varoke.getCurrentTimestamp() + "-" + new Random().nextInt(99));
	}

	public byte[] fillAsset(HashMap<String, Object> values, String file) {
		String asset = new String(getAsset(file));
		for(Entry<String, Object> entry : values.entrySet()){
			asset = asset.replace("{" + entry.getKey() + "}",StringEscapeUtils.escapeHtml4(entry.getValue().toString()));
		}
		return asset.getBytes();
	}

	public boolean checkName(String username) {
		for(char c : username.toCharArray())
		{
			if(!(Character.isDigit(c) || Character.isLetter(c) || c == '.' || c == '-' || c == '=' || c == ':'))
				return false; // only numbers, letters, - and .
		}
		return true;
	}
	
	public boolean checkMail(String mail){
		for(char c : mail.toCharArray())
		{
			if(!(Character.isDigit(c) || Character.isLetter(c) || c == '@' || c == '.'))
				return false; // only numbers, letters, - and .
		}
		return mail.contains("@") && mail.contains(".");
	}
}
