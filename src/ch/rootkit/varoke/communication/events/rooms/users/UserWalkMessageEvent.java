package ch.rootkit.varoke.communication.events.rooms.users;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class UserWalkMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomUser roomUser = session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId());
		if(roomUser == null) 
			return;
		roomUser.setIdle(false);
		roomUser.setGoal(new Point(event.readInt(), event.readInt()));
		roomUser.setPathFinderCollection();
	}

}
