package ch.rootkit.varoke.habbohotel.navigator;

import java.util.List;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;

public class NavigatorCategory {
	
	private int Id;
	private String Title;
	private List<RoomData> Rooms;
	private boolean collapsed;
	private boolean thumbnail;
	
	public NavigatorCategory(int id, String title,boolean collaps,boolean thumb, List<RoomData> rooms){
		Id = id;
		Title = title;
		collapsed = collaps;
		thumbnail = thumb;
		Rooms = rooms;
	}
	
	public int getId(){ 
		return Id; 
	}
	
	public String getTitle(){ 
		return Title; 
	}
	
	public boolean showCollapsed(){ 
		return collapsed; 
	}
	
	public boolean showThumbnail(){ 
		return thumbnail; 
	}
	
	public List<RoomData> getRooms(){ 
		return Rooms; 
	}
	
	public void serialize(ServerMessage message) throws Exception{
		 message.writeString(getTitle()); // code
         message.writeString(""); // title
         message.writeInt(1);
         message.writeBoolean(showCollapsed());
         message.writeInt(showThumbnail() ? 1 : 0);
         message.writeInt(getRooms().size());
         for(RoomData room : getRooms()){
        	 room.serialize(message);
         }
	}
}
