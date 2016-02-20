package ch.rootkit.varoke.habbohotel.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Logger;

public class SafeChat {
	
	private static List<String> unsafe = new ArrayList<String>();
	private static HashMap<String, String> filter = new HashMap<String, String>();
	
	public static void init() throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Initializing Wordfilter ");
		unsafe = Varoke.getFactory().getVarokeFactory().readUnsafeWords();
		filter = Varoke.getFactory().getVarokeFactory().readFilter();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	
	public static boolean isSafe(String message){
		for(String v : unsafe){
			if(message.toLowerCase().contains(v.toLowerCase()))
				return false;
		}
		return true;
	}
	
	public static String filter(String message){
		String filtered = message;
		for(Entry<String, String> v : filter.entrySet()){
			filtered = filtered.replace(v.getKey(), v.getValue());
		}
		return filtered;
	}
}
