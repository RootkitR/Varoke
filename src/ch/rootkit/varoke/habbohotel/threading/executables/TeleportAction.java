package ch.rootkit.varoke.habbohotel.threading.executables;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.utils.Logger;

public class TeleportAction implements Runnable{

	final RoomItem roomItem;
	final RoomUser roomUser;
	public TeleportAction(RoomItem item, RoomUser user){
		roomItem = item;
		roomUser = user;
	}
	@Override
	public void run() {
		try {
			boolean exit = false;
			boolean passed1 = false;
			boolean passed2 = false;
			while(!exit){
				if(roomItem.inFront().equals(roomUser.getPosition()) && !passed1){
					roomUser.setGoal(new Point(roomItem.getX(), roomItem.getY()));
					roomUser.setPathFinderCollection(true);
					passed1 = true;
				}
				if(roomUser.getPosition().equals(new Point(roomItem.getX(), roomItem.getY())) && !passed2){
					passed2 = true;
					int otherTele = Varoke.getFactory().getInventoryFactory().getOtherTele(roomItem.getId());
					int roomId = Varoke.getFactory().getInventoryFactory().getRoomByItem(otherTele);
					if(otherTele == 0 || roomId == 0){
						exit = true;
						continue;
					}
					if(roomId == roomItem.getRoomId()){
						RoomItem item2 = roomItem.getRoom().getItemManager().getItem(otherTele);
						if(item2 == null)
							return;
						roomUser.setPosition(new Point(item2.getX(), item2.getY()));
						roomUser.setRotation(item2.getRotation());
						item2.setExtraData("2");
						item2.update();
						roomUser.updateStatus();
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Logger.printErrorLine("Error in TeleportAction.java : " + e.getMessage());
		}
	}

}
