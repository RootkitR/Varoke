package ch.rootkit.varoke.habbohotel.threading.executables;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.utils.Logger;

public class UpdateItemAction implements Runnable{

	final RoomItem roomItem;
	final String ExtraData;
	public UpdateItemAction(RoomItem item, String extraData){
		roomItem = item;
		ExtraData = extraData;
	}
	@Override
	public void run() {
		if(roomItem != null){
			try {
				roomItem.setExtraData(ExtraData);
				roomItem.update();
			} catch (Exception e) {
				e.printStackTrace();
				Logger.printErrorLine("Error caught in UpdateItemAction : " +  e.getMessage());
			}
		}
	}

}
