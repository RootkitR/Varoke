package ch.rootkit.varoke.web.cookie;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;

public class CookieManager {

	private HashMap<String, Cookie> cookies;
	private SecureRandom random;
	public CookieManager(){
		cookies = new HashMap<String, Cookie>();
		random = new SecureRandom();
	}
	
	public Cookie createCookie(){
		String id = getNewIdentifier();
		Cookie c = new Cookie(id);
		cookies.put(id, c);
		return c;
	}
	
	public String getNewIdentifier(){
		return new BigInteger(130, random).toString(32); 
	}
	
	public Cookie get(String sessionId){
		return cookies.get(sessionId);
	}
}
