package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.users.HomeRoomMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class SetHomeRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int roomId = event.readInt();
		Varoke.getFactory().getUserFactory().updateValue(session.getHabbo().getId(), "home_room", roomId);
		session.getHabbo().setHome(roomId);
		session.sendComposer(new HomeRoomMessageComposer(roomId));
	}

}
