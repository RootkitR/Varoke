package ch.rootkit.varoke.habbohotel.threading.executables;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.rooms.Room;

public class TerminateRoom implements Runnable{

	final Room room;
	public TerminateRoom(Room r){
		room = r;
	}
	@Override
	public void run() {
		if(room != null && room.getRoomUsers().size() == 0){
			Varoke.getGame().getRoomManager().removeRoom(room.getId());
			room.dispose();
		}
	}

}
