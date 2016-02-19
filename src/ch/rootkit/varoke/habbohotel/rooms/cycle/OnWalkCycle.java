package ch.rootkit.varoke.habbohotel.rooms.cycle;

import java.util.List;

import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.utils.Logger;

public class OnWalkCycle extends UserCycle {

	public OnWalkCycle(Room r) {
		super(r);
	}

	@Override
	public void onCycle(RoomUser roomUser) {
		List<RoomItem> newSquare = getRoom().getItemManager().getItemsOnSquare(roomUser.getPosition().getX(), roomUser.getPosition().getY());
		List<RoomItem> oldSquare = getRoom().getItemManager().getItemsOnSquare(roomUser.getOldPosition().getX(), roomUser.getOldPosition().getY());
		
		for(RoomItem item : oldSquare){
			try{
				if(item != null)
					item.getInteractor().onWalkOff(roomUser);
			}catch(Exception ex){
				Logger.printErrorLine(ex.toString());
			}
		}
		
		if(roomUser.getGoal().equals(new Point(-1,-1)))
			return;
		
		for(RoomItem item : newSquare){
			try{
				if(item != null)
					item.getInteractor().onWalk(roomUser);
			}catch(Exception ex){
				Logger.printErrorLine(ex.toString());
			}
		}
	}

}
