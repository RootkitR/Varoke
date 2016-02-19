package ch.rootkit.varoke.communication.events.badges;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.badges.UserBadgesMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GetUserBadgesMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		Session target = Varoke.getSessionManager().getSessionByUserId(event.readInt());
		if(target == null)
			return;
		session.sendComposer(new UserBadgesMessageComposer(target.getHabbo().getBadgeComponent()));
	}

}
