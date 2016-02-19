package ch.rootkit.varoke.communication.events.rooms.users;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class UserDanceMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomUser roomUser = session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId());
		if(roomUser == null) return;
		roomUser.unidle();
		int danceId = event.readInt();
		if(danceId < 0 || danceId > 4)
			danceId = 0;
		if(danceId > 0 && roomUser.getCarryItem() > 0)
			roomUser.carry(0);
		roomUser.enableDance(danceId);
	}

}
