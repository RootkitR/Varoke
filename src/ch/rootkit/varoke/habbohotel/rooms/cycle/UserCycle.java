package ch.rootkit.varoke.habbohotel.rooms.cycle;

import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public abstract class UserCycle {
	Room room;
	public UserCycle(Room r){
		room = r;
	}
	
	public Room getRoom(){
		return room;
	}
	
	public abstract void onCycle(RoomUser roomUser);
}
