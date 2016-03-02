package ch.rootkit.varoke.habbohotel.threading.executables;

import java.util.Random;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.utils.Logger;

public class DiceAction implements Runnable{

	final RoomItem roomItem;
	public DiceAction(RoomItem item){
		roomItem = item;
	}
	@Override
	public void run() {
		try {
			roomItem.setExtraData((new Random().nextInt(6) + 1) + "");
			roomItem.update();
		} catch (Exception e) {
			Logger.printErrorLine("Error in VendingAction.java : " + e.getMessage());
		}
	}

}
