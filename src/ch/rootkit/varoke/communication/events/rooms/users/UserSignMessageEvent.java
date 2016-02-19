package ch.rootkit.varoke.communication.events.rooms.users;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class UserSignMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomUser roomUser = session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId());
		if(roomUser == null) return;
		int sign = event.readInt();
		roomUser.addStatus("sign", sign +"");
		roomUser.updateStatus();
		roomUser.setSignTimer(10);
	}

}
