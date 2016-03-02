package ch.rootkit.varoke;

import java.util.Date;

import ch.rootkit.varoke.database.Database;
import ch.rootkit.varoke.database.factories.FactoryManager;
import ch.rootkit.varoke.habbohotel.Game;
import ch.rootkit.varoke.habbohotel.sessions.SessionManager;
import ch.rootkit.varoke.network.ConnectionListener;
import ch.rootkit.varoke.network.crypto.RSA;
import ch.rootkit.varoke.utils.Configuration;
import ch.rootkit.varoke.utils.Logger;
import ch.rootkit.varoke.utils.RSAConfig;
import ch.rootkit.varoke.web.WebServer;

public class Varoke {
	
	private static Database db;
	private static SessionManager sessionManager;
	private static long serverStarted;
	public static long serverReady;
	public static String Version = "PRODUCTION-201602082203-712976078";
	private static Game game;
	private static RSA rsa;
	private static FactoryManager factoryManager;
	
	public static void initialize() throws Exception{
		serverStarted = new Date().getTime();
		System.out.println("Varoke Emulator by Rootkit [" + Version + "]");
		System.out.println("");
		Configuration.initialize();
		db = new Database();
		factoryManager = new FactoryManager();
		game = new Game();
		game.initialize();
		RSAConfig.init(Varoke.Version);
		rsa = new RSA();
		rsa.init();
		sessionManager = new SessionManager();
		ConnectionListener.startBootstrap();
		WebServer.start();
		serverReady = new Date().getTime();
		Logger.printVarokeLine("Ready (" + (serverReady - serverStarted) + "ms)");
	}
	
	public static Database getDatabase() throws Exception{
		if(db == null)
			db = new Database();
		return db;
	}
	
	public static SessionManager getSessionManager(){
		return sessionManager;
	}
	
	public static Game getGame(){
		return game;
	}
	
	public static RSA getRSA(){
		return rsa;
	}
	
	public static long getCurrentTimestamp(){
		return System.currentTimeMillis() / 1000L;
	}
	
	public static FactoryManager getFactory(){
		return factoryManager;
	}
	
	public static boolean stringToBool(String x){
		return x.equals("1");
	}
	
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException nfe) {}
		return false;
	}
	
	public static String getCurrentRamPercentage(){
		return ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 0x100000) + "MB von " + Runtime.getRuntime().freeMemory() / 0x100000 + "MB";
	}
}
