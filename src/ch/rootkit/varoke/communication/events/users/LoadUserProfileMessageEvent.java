package ch.rootkit.varoke.communication.events.users;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.badges.UserBadgesMessageComposer;
import ch.rootkit.varoke.communication.composers.users.UserProfileMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class LoadUserProfileMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int userId = event.readInt();
		if(userId == 0)
			userId = 1;
		Habbo h = Varoke.getFactory().getUserFactory().getHabbo(userId);
		if(h == null)
			return;
		session.sendComposer(new UserProfileMessageComposer(session, h));
		session.sendComposer(new UserBadgesMessageComposer(Varoke.getFactory().getBadgeFactory().getBadgeComponent(userId)));
	}
}
