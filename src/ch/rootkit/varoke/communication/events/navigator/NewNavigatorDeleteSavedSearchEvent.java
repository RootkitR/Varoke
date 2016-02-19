package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.communication.composers.navigator.NavigatorSavedSearchesComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class NewNavigatorDeleteSavedSearchEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.getHabbo().removeSearch(event.readInt());
		session.sendComposer(new NavigatorSavedSearchesComposer(session.getHabbo().getSearches()));
	}

}
