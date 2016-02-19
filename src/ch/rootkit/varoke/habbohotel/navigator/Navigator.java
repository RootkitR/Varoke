package ch.rootkit.varoke.habbohotel.navigator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.utils.Logger;

public class Navigator {
	public List<NavigatorCategory> categories;
	public List<FlatCat> flatcats;
	public Navigator(){
	}
	public void initialize() throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Initializing Navigator ");
		categories = Varoke.getFactory().getNavigatorFactory().readCategories();
		flatcats = Varoke.getFactory().getNavigatorFactory().readFlatCats();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	public int getNavigatorLength(String value, Habbo session)
    {
        switch (value)
        {
            case "official_view":
                return categories.size();
            case "myworld_view":
                return 3;
            case "hotel_view":
                return 1;
            case "roomads_view":
            	return getFlatsCount(session);
        }
        return 1;
    }
	public List<FlatCat> getFlats(){ return flatcats;}
	public int getFlatsCount(Habbo session){
		int result = 0;
		for(FlatCat f : flatcats){
			if(session.getRank() >= f.getMinRank())
				result++;
		}
		return result;
	}
	public NavigatorCategory getCategory(String value){
		for(NavigatorCategory nc : categories){
			if(nc.getTitle().equals(value))
				return nc;
		}
		return null;
	}
	public NavigatorCategory getCategoryById(int value){
		for(NavigatorCategory nc : categories){
			if(nc.getId() == value)
				return nc;
		}
		return null;
	}
	public FlatCat getFlatCat(String value){
		
		for(FlatCat fc : flatcats){
			if(fc.getEventCatName().equals(value))
				return fc;
		}
		return null;
	}
	public FlatCat getFlatCatById(int value){
		
		for(FlatCat fc : flatcats){
			if(fc.getId() == value)
				return fc;
		}
		return null;
	}
	public boolean isCateogry(String value){
		for(NavigatorCategory nc : categories){
			if(nc.getTitle().equals(value))
				return true;
		}
		return false;
	}
	public boolean isFlatcat(String value){
		for(FlatCat fc : flatcats){
			if(fc.getEventCatName().equals(value))
				return true;
		}
		return false;
	}
	public List<Room> getRoomsByFlatcat(int id){
		List<Room> rooms = new ArrayList<Room>();
		for(Room r : Varoke.getGame().getRoomManager().loadedRooms.values()){
			if(r.getData().getCategory() == id)
				rooms.add(r);
		}
		return rooms;
	}
	public void serializeNavigator(String staticId, boolean direct, Session session, ServerMessage message) throws Exception {
		
		if (StringUtils.isNullOrEmpty(staticId) || staticId.equals("official")) staticId = "official_view";
        if (!staticId.equals("hotel_view") && !staticId.equals("roomads_view") && !staticId.equals("myworld_view") &&
            !staticId.startsWith("category__") && !staticId.equals("official_view"))
        {
            message.writeString(staticId); // code
            message.writeString(""); // title
            message.writeInt(1); // 0 : no button - 1 : Show More - 2 : Show Back button
            message.writeBoolean(!staticId.equals("my") && !staticId.equals("official-root") && !staticId.equals("popular")); // collapsed
            message.writeInt(staticId.equals("official-root") ? 1 : 0); // 0 : list - 1 : thumbnail
        }
        switch (staticId)
        {
	        case "official_view":
	        {
	            for(NavigatorCategory nc : categories)
	            	nc.serialize(message);
	            break;
	        }
	        case "roomads_view":
	        {
	        	for(FlatCat fcat : flatcats)
	        		fcat.serialize(message, true);
	        	break;
	        }
	        case "hotel_view":
	        {
	        	serializeNavigator("popular", false, session, message);
	        	break;
	        }
	        case "popular":
	        {
	        	serializePopularRooms(message);
	        	break;
	        }
	        case "myworld_view":
	        {
	        	serializeNavigator("my", false, session, message);
	        	serializeNavigator("favorites", false, session, message);
	        	serializeNavigator("my_groups", false,session,message);
	        	break;
	        }
	        case "my":
	        {
	        	serializeUserRooms(message, session);
	        	break;
	        }
	        case "favorites":
	        {
	        	message.writeInt(0);
	        	break;
	        }
	        case "my_groups":
	        {
	        	message.writeInt(0);
	        	break;
	        }
        }
	}
	public void serializeUserRooms(ServerMessage message, Session session) throws Exception{
		List<RoomData> myRooms = Varoke.getFactory().getRoomFactory().readUserRooms(session.getHabbo());
		message.writeInt(myRooms.size());
		for(RoomData roomData : myRooms){
			roomData.serialize(message);
		}
	}
	public void serializePopularRooms(ServerMessage message) throws Exception{
		List<Room> rooms = new ArrayList<Room>(Varoke.getGame().getRoomManager().loadedRooms.values());
		Collections.sort(rooms, new Comparator<Room>() {
			public int compare(Room o1, Room o2) {
				return o1.users() - o2.users();
		    }
		});
		message.writeInt(rooms.size());
		for(Room room : rooms){
			room.getData().serialize(message);
		}
	}
	public void performSearch(String value1, String search, Session session, ServerMessage message) throws Exception{
		String type = "";
		
		if(search.contains(":"))
		{type = search.split(":")[0];search = search.split(":")[1];}
		message.writeString(type);
		message.writeString(search);
		message.writeInt(1);
		message.writeBoolean(false);
		message.writeInt(1);
		List<RoomData> results = new ArrayList<RoomData>();
		switch(type){
			case "owner":
				results = Varoke.getFactory().getRoomFactory().readUserRooms(Varoke.getFactory().getUserFactory().getIdFromUser(search));
				break;
			case "tag":
				results = Varoke.getFactory().getRoomFactory().searchByTag(search);
				break;
			case "group": //TODO groups :D
			case "roomname":
			default:
				results = Varoke.getFactory().getRoomFactory().search(search);
				break;
		}
		message.writeInt(results.size());
		for(RoomData rd : results)
			rd.serialize(message);
	}
}
