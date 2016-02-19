package ch.rootkit.varoke.communication.events.catalog;

import ch.rootkit.varoke.communication.composers.catalog.CatalogueIndexMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GetCatalogIndexMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new CatalogueIndexMessageComposer(event.readString(), session));
	}

}
