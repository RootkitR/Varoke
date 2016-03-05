package ch.rootkit.varoke.web.cookie;

import java.util.HashMap;

public class Cookie {
	private HashMap<String, String> cookies;
	private String identifier;
	
	public Cookie(String id){
		identifier = id;
		cookies = new HashMap<String, String>();
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public boolean addCookie(String key, String value){
		if(cookies.containsKey(key))
			return false;
		cookies.put(key, value);
		return true;
	}
	
	public boolean removeCookie(String key){
		if(!cookies.containsKey(key))
			return false;
		cookies.remove(key);
		return true;
	}
	
	public String getCookie(String key){
		if(cookies.containsKey(key))
			return cookies.get(key);
		return "";
	}
	
	public boolean hasCookie(String key){
		return cookies.containsKey(key);
	}
}
