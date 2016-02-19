package ch.rootkit.varoke.communication.events;

import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public interface MessageEvent {
	public void handle(Session session, ClientMessage event) throws Exception;
}
