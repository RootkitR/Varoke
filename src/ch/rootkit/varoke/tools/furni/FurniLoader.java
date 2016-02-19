package ch.rootkit.varoke.tools.furni;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class FurniLoader {
	// Downloads everything from the things :33
	public static void download(String furniData, String furniHost) {
		long started = new Date().getTime();
		System.out.println("Loading FurniData........");
		FurniDataParser parser = new FurniDataParser(furniData);
		parser.parseXmlFile();
		parser.parseDocument();
		String dirName = "furniture_" + (new Date().getTime() / 1000);
		new File(dirName).mkdirs();
		Path target = Paths.get(System.getProperty("user.dir"), dirName);
		int completed = 0;
		int failed = 0;
		for(FurniData furni : parser.getItems()){
			try{
				System.out.print("Downloading " + furni.getFurniName());
				URL furnilink = new URL(furniHost + "/" + furni.getRevision() + "/" + furni.getFurniName() + ".swf");
				InputStream in = furnilink.openStream();
				Files.copy(in, Paths.get(target.toString(), furni.getFurniName() + ".swf"), StandardCopyOption.REPLACE_EXISTING);
				in.close();
				System.out.println(" completed!");
				completed++;
			}catch(Exception ex){
				System.out.println(" failed!");
				failed++;
			}
		}
		System.out.println("Downloaded " + (completed + failed) + " furnis (" + completed + " successfull, " + failed + " failed)");
		long finished = new Date().getTime();
		System.out.println("Finished in " + ((finished - started) / 1000) + " seconds!");
		
	}
}
