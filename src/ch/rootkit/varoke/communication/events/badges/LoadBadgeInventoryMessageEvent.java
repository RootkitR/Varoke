package ch.rootkit.varoke.communication.events.badges;

import ch.rootkit.varoke.communication.composers.badges.LoadBadgesWidgetMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class LoadBadgeInventoryMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new LoadBadgesWidgetMessageComposer(session.getHabbo().getBadgeComponent()));
	}

}
