package ch.rootkit.varoke.habbohotel.rooms.cycle;

import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public abstract class ItemCycle {
	Room room;
	public ItemCycle(Room r){
		room = r;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public abstract void onCycle(RoomItem roomItem);
}
