package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.communication.composers.navigator.FlatCategoriesMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class NavigatorGetFlatCategoriesMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new FlatCategoriesMessageComposer(session));
	}

}
