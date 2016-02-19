package ch.rootkit.varoke.habbohotel.rooms.cycle;

import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class SignCycle extends UserCycle {

	public SignCycle(Room r) {
		super(r);
	}

	@Override
	public void onCycle(RoomUser roomUser) {
		if(roomUser.getSignTimer() != -1){
			if(roomUser.getSignTimer() == 0)
				roomUser.removeAndUpdateStatus("sign");
			roomUser.setSignTimer(roomUser.getSignTimer() - 1);
		}
	}

}
