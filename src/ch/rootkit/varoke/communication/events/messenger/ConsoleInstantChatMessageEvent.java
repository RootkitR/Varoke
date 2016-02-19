package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class ConsoleInstantChatMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.getHabbo().getMessenger().handleMessage(event.readInt(), event.readString());
	}

}
