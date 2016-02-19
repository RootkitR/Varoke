package ch.rootkit.varoke.communication.events.rooms.users;

import ch.rootkit.varoke.communication.composers.rooms.users.RoomUserActionMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomUserActionMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		if(session.getHabbo().getCurrentRoom() == null) return;
		RoomUser roomUser = session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId());
		if(roomUser == null) return;
		roomUser.unidle();
		int action = event.readInt();
		roomUser.getRoom().sendComposer(new RoomUserActionMessageComposer(roomUser.getVirtualId(), action));
		if(action == 5)
			roomUser.idle();
	}
}
