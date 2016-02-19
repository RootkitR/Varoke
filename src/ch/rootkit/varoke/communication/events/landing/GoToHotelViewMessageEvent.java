package ch.rootkit.varoke.communication.events.landing;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GoToHotelViewMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		if(session.getHabbo().getCurrentRoomId() > 0)
			session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId()).removeFromRoom();
	}

}
