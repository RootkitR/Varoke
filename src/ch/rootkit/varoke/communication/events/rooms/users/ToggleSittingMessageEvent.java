package ch.rootkit.varoke.communication.events.rooms.users;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class ToggleSittingMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		if(session.getHabbo().getCurrentRoom() == null) return;
		RoomUser roomUser = session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId());
		if(roomUser == null) return;
		if(roomUser.getStatusses().containsKey("sit"))
			roomUser.getStatusses().remove("sit");
		else
			roomUser.getStatusses().put("sit", "0.55");
		roomUser.updateStatus();
	}
}
