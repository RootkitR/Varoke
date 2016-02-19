package ch.rootkit.varoke.communication.events.wardrobe;

import ch.rootkit.varoke.communication.composers.wardrobe.LoadWardrobeMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class WardrobeMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new LoadWardrobeMessageComposer(session.getHabbo().getWardrobe()));
	}

}
