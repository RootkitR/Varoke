package ch.rootkit.varoke.habbohotel.navigator;

import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;

public class FlatCat {
	
	private int Id;
	private String Caption;
	private int MinRank;
	
	public FlatCat(int id, String caption, int min_rank){
		Id = id;
		Caption = caption;
		MinRank = min_rank;
	}
	
	public int getId(){
		return Id; 
	}
	
	public String getCaption(){ 
		return Caption; 
	}
	
	public String getEventCatName(){ 
		return "eventcategory__" + getId();
	}
	
	public int getMinRank(){ 
		return MinRank; 
	}
	
	public void serialize(ServerMessage message, boolean search) throws Exception{
		message.writeString(getEventCatName());
		message.writeString(getCaption());
		message.writeInt(1);
		message.writeBoolean(!search);
		message.writeInt(0);
		List<Room> rooms = Varoke.getGame().getNavigator().getRoomsByFlatcat(getId());
		message.writeInt(rooms.size());
		for(Room r : rooms)
			r.getData().serialize(message, true);
		rooms.clear();
		rooms = null;
	}
}
