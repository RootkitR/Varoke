package ch.rootkit.varoke.habbohotel.users;


import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Logger;

public class Permissions {
	
	HashMap<Integer, List<String>> permissions;
	
	public Permissions() throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Loading Permissions ");
		permissions = Varoke.getFactory().getUserFactory().readPermissions();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	
	public boolean hasFuse(int Rank, String key){
		return permissions.containsKey(Rank) && permissions.get(Rank).contains(key);
	}
}
