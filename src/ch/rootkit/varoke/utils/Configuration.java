package ch.rootkit.varoke.utils;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import com.mysql.jdbc.StringUtils;

public class Configuration {
	
	private static HashMap<String,String> configurations;
	
	public static void initialize() throws Exception{
		configurations = new HashMap<String, String>();
		Path p = Paths.get(System.getProperty("user.dir"), "configuration.varoke");
		for(String line : Files.readAllLines(p, Charset.defaultCharset())){
			if((!line.startsWith("##") || !line.startsWith("//")) && !StringUtils.isNullOrEmpty(line))
			{
				String[] args = line.split("=");
				configurations.put(args[0], args.length > 1 ? args[1] : "");
			}
		}
	}
	
	public static String get(String key){
		if(configurations.containsKey(key))
			return configurations.get(key);
		return "Key not found!";
	}
}
