package ch.rootkit.varoke;

import ch.rootkit.varoke.tools.catalog.Tablefix;
import ch.rootkit.varoke.tools.furni.FurniLoader;

public class Main {
	public static void main(String[] args){
		try{
			for(int i = 0; i < args.length; i++){
				if(args[i].equals("download")){
					FurniLoader.download(args[i + 1], args[i + 2]);
					System.exit(0);
				}
				if(args[i].equals("fix")){
					Tablefix.start();
					System.exit(0);
				}
			}
			Varoke.initialize();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
