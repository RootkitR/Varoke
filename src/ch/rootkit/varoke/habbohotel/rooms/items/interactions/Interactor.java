package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public abstract class Interactor {
	
	private RoomItem roomItem;
	public Interactor(RoomItem item){
		roomItem = item;
	}
	public void onPlace(RoomUser user) throws Exception{
		
	}
	public void onTrigger(RoomUser user, boolean withRights) throws Exception{
		
	}
	public void onWalk(RoomUser user) throws Exception{
		
	}
	public void onWalkOff(RoomUser user) throws Exception{
		
	}
	public boolean isWalkable() {
		return true;
	}
	public RoomItem getItem() {
		return roomItem;
	}
	
}
