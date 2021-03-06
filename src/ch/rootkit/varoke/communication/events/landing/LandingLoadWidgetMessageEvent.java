package ch.rootkit.varoke.communication.events.landing;

import ch.rootkit.varoke.communication.composers.landing.LandingPromosMessageComposer;
import ch.rootkit.varoke.communication.composers.landing.LandingWidgetMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class LandingLoadWidgetMessageEvent implements MessageEvent{

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new LandingWidgetMessageComposer(event.readString()));
		session.sendComposer(new LandingPromosMessageComposer());
	}

}
