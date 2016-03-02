package ch.rootkit.varoke.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


public class RSAConfig {
	
	public static HashMap<String, String> rsa;
	
	public static void init(String release){
	    try {
	    	rsa = new HashMap<String, String>();
	    	Path p = Paths.get(System.getProperty("user.dir"), release + ".rsa");
	      List<String> lines = Files.readAllLines(p, Charset.defaultCharset());

	      for (String line : lines) {
	    	  String[] args = line.split("=");
	    	  rsa.put(args[0], args[1]);
	      }
	    } catch (IOException e) {
	      System.out.println(e);
	    }
	}
	
	/**
	 * 
	 * @param key The key which should be registered in CLIENTVERSION.rsa
	 * @return Returns the value of the key (if not found it returns an empty String)
	 */
	public static String get(String key){
		if(rsa.containsKey(key))
			return rsa.get(key);
		return "";
	}
}
