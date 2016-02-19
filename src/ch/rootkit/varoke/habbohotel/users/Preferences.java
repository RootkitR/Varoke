package ch.rootkit.varoke.habbohotel.users;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Logger;

public class Preferences {

	private int UserId;
	private String Volume;
	private int NavigatorX;
	private int NavigatorY;
	private int NavigatorHeight;
	private int NavigatorWidth;
	public Preferences(int userId, String volume, int naviX, int naviY, int naviHeight, int naviWidth){
		UserId = userId;
		Volume = volume;
		NavigatorX = naviX;
		NavigatorY = naviY;
		NavigatorHeight = naviHeight;
		NavigatorWidth = naviWidth;
	}
	public int getUserId(){ return UserId;}
	public String getVolume(){ return Volume;}
	public int getNavigatorX(){ return NavigatorX;}
	public int getNavigatorY(){ return NavigatorY;}
	public int getNavigatorHeight(){ return NavigatorHeight;}
	public int getNavigatorWidth(){ return NavigatorWidth;}
	public Preferences setNavigatorX(int x){ NavigatorX = x; return this;}
	public Preferences setNavigatorY(int y){ NavigatorY = y; return this;}
	public Preferences setNavigatorHeight(int height){ NavigatorHeight = height; return this;}
	public Preferences setNavigatorWidth(int width){ NavigatorWidth = width; return this;}
	public void save(){
		try {
			Varoke.getFactory().getUserFactory().savePreferences(this);
		} catch (Exception e) {
			Logger.printErrorLine("Error saving User Preferences.");
		}
	}
}
