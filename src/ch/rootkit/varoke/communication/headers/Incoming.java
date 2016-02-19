package ch.rootkit.varoke.communication.headers;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.utils.Logger;

public class Incoming {
	public static HashMap<String, Short> incoming;
	public static void init(String release){
	    try {
	    	incoming = new HashMap<String, Short>();
	    	Path p = Paths.get(System.getProperty("user.dir"), release + ".incoming");
	      List<String> lines = Files.readAllLines(p, Charset.defaultCharset());

	      for (String line : lines) {
	    	  if(line.startsWith("[") || StringUtils.isNullOrEmpty(line))
	    		  continue;
	    	  line.replace(" ", "");
	    	  String[] args = line.split("=");
	    	  if (args[1].contains("/"))
                  args[1] = args[1].split("/")[0];
	    	  if(args[1].equals("-1"))
	    		  continue;
	    	  if(!incoming.containsKey(args[0]) && !incoming.containsValue(Short.parseShort(args[1])))
	    		  incoming.put(args[0], Short.parseShort(args[1]));
	    	  else if(incoming.containsKey(args[0]))
	    		  Logger.printWarningLine("There's already a Packet called " + args[0]);
	    	  else if(incoming.containsValue(Short.parseShort(args[1])))
	    		  Logger.printWarningLine("There's already a Packet with ID #" + args[1]);
	      }
	    } catch (IOException e) {
	      System.out.println(e);
	    }
	}
	public static short get(String s){
		if(incoming.containsKey(s))
			return incoming.get(s);
		return 0;
	}
}
