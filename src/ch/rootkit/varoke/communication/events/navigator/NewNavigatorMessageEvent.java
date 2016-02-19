package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.communication.composers.navigator.NavigatorMetaDataComposer;
import ch.rootkit.varoke.communication.composers.navigator.NavigatorSavedSearchesComposer;
import ch.rootkit.varoke.communication.composers.navigator.NewNavigatorSizeMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class NewNavigatorMessageEvent implements MessageEvent{

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new NavigatorMetaDataComposer());
		if(session.getHabbo().getPreferences() != null)
			session.sendComposer(new NewNavigatorSizeMessageComposer(session.getHabbo().getPreferences()));
		session.sendComposer(new NavigatorSavedSearchesComposer(session.getHabbo().getSearches()));
	}

}
