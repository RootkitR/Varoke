package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RequestFriendMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.getHabbo().getMessenger().createRequest(Varoke.getFactory().getUserFactory().getIdFromUser(event.readString()));
	}

}
