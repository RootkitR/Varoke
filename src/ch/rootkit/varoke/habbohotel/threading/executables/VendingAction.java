package ch.rootkit.varoke.habbohotel.threading.executables;

import java.util.Random;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.utils.Logger;

public class VendingAction implements Runnable{

	final RoomItem roomItem;
	final RoomUser roomUser;
	public VendingAction(RoomItem item, RoomUser user){
		roomItem = item;
		roomUser = user;
	}
	@Override
	public void run() {
		try {
			if(roomItem != null && roomUser != null){
				int vendingId = 0;
				if(roomItem.getBaseItem().getVendingIds().size() > 1){
					vendingId = roomItem.getBaseItem().getVendingIds().get(
							new Random().nextInt(
									roomItem.getBaseItem().getVendingIds().size() - 1)
							);
				}else if(roomItem.getBaseItem().getVendingIds().size() == 1){
					vendingId = roomItem.getBaseItem().getVendingIds().get(0);
				}
				roomUser.carry(vendingId);				
				roomItem.setExtraData("0");
				roomItem.update();
			}
		} catch (Exception e) {
			Logger.printErrorLine("Error in VendingAction.java : " + e.getMessage());
		}
	}

}
