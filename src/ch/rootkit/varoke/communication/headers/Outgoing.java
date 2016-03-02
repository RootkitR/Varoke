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

public class Outgoing {
	public static HashMap<String, Short> outgoing;
	public static void init(String release){
	    try {
	    	outgoing = new HashMap<String, Short>();
	    	Path p = Paths.get(System.getProperty("user.dir"), release + ".outgoing");
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
	    	  if(!outgoing.containsKey(args[0]) && !outgoing.containsValue(Short.parseShort(args[1])))
	    		  outgoing.put(args[0], Short.parseShort(args[1]));
	    	  else if(outgoing.containsKey(args[0]))
	    		  Logger.printWarningLine("There's already a Packet named " + args[0]);
	    	  else if(outgoing.containsValue(Short.parseShort(args[1])))
	    		  Logger.printWarningLine("There's already a Packet with ID #" + args[1]);
	      }
	    } catch (IOException e) {
	      System.out.println(e);
	    }
	}
	
	/**
	 * 
	 * @param key The key which should be registered in CLIENTVERSION.outgoing
	 * @return Returns the value of the key as an short (If the key was not found it returns a 0)
	 */
	public static short get(String s){
		if(outgoing.containsKey(s))
			return outgoing.get(s);
		return 0;
	}
}
