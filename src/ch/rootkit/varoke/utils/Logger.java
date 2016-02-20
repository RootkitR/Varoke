package ch.rootkit.varoke.utils;

public class Logger {

	public static void printIncomingLine(short header, boolean registered, String packet, String classname){
		System.out.println("[Varoke] : [" + classname + "] [" + header + "] : " + packet);
	}
	
	public static void printVarokeLine(String line){
		System.out.println("[Varoke] : " + line);
	}
	
	public static void printWarningLine(String line){
		System.err.println("[Varoke] : [WARNING] : " + line);
	}
	
	public static void printErrorLine(String line){
		System.err.println("[Varoke] : [ERROR] : " + line);
	}
	
	public static void printVaroke(String l){
		System.out.print("[Varoke] : " + l);
	}
	
	public static void printLine(String l){
		System.out.println(l);
	}
}
