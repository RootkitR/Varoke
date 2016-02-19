package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.communication.composers.navigator.CanCreateRoomMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.utils.Logger;

public class CanCreateRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new CanCreateRoomMessageComposer(session.getHabbo()));
		Logger.printVarokeLine(getClass().getName());
	}

}
