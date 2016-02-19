package ch.rootkit.varoke.habbohotel.rooms.cycle;

import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;


public class RoomCycle implements Runnable{

	int count = 0;
	Room room;
	boolean stopped = false;
	List<ItemCycle> itemCycles;
	List<UserCycle> userCycles;
	public RoomCycle(Room r){
		room = r;
		itemCycles = new ArrayList<ItemCycle>();
		userCycles = new ArrayList<UserCycle>();
		itemCycles.add(new GiftTimerCycle(room));
		userCycles.add(new WalkCycle(room));
		userCycles.add(new SignCycle(room));
		userCycles.add(new OnWalkCycle(room));
	}
	public Room getRoom(){ return room;}
	@Override
	public void run() {
		if(!stopped){
			Varoke.getGame().getThreadPool().executeScheduled(this, 500);
		}
		count++;
		for(RoomUser roomUser : new ArrayList<RoomUser>(getRoom().getRoomUsers()))
		{
			for(UserCycle cycle : userCycles){
				cycle.onCycle(roomUser);
			}
		}
		for(RoomItem roomItem : new ArrayList<RoomItem>(getRoom().getItemManager().getFloorItems())){
			for(ItemCycle cycle : itemCycles){
				cycle.onCycle(roomItem);
			}
		}
	}
	public String getError(int l){
		switch(l)
		{
		case 0:
			return "Walking";
		case 1:
			return "Sign";
		default:
			return "Cycle";
		}
	}
	
	public void stop(){
		this.stopped = true;
	}
}
