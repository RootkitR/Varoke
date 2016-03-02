package ch.rootkit.varoke.utils;

public class Logger {

	/**
	 * Prints "[Varoke] : [CLASSNAME] [HEADER] : Packet-Body"
	 * @param header The Incoming ID
	 * @param packet The Body of the Incoming Packet
	 * @param classname The Classname of the Packet-Handler (MessageEvent)
	 */
	public static void printIncomingLine(short header, String packet, String classname){
		System.out.println("[Varoke] : [" + classname + "] [" + header + "] : " + packet);
	}
	
	/**
	 * Prints "[Varoke] : LINE"
	 * @param line The Value which should be printed
	 */
	public static void printVarokeLine(String line){
		System.out.println("[Varoke] : " + line);
	}
	
	/**
	 * Prints "[Varoke] : [WARNING] : line" good for warnings (obvious)
	 * @param line The Value which should be printed
	 */
	public static void printWarningLine(String line){
		System.err.println("[Varoke] : [WARNING] : " + line);
	}
	
	/**
	 *  Prints "[Varoke] : [ERROR] : line" good for errors
	 * @param line The Value which should be printed
	 */
	public static void printErrorLine(String line){
		System.err.println("[Varoke] : [ERROR] : " + line);
	}
	
	/**
	 * Prints "[Varoke] : line" (without wordwrap)
	 * @param line The Value which should be printed
	 */
	public static void printVaroke(String line){
		System.out.print("[Varoke] : " + line);
	}
	
	/**
	 * Just prints the value (with wordwrap)
	 * @param line The Value which should be printed
	 */
	public static void printLine(String line){
		System.out.println(line);
	}
}
