package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.messenger.ConsoleInvitationMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class ConsoleInviteFriendsMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int size = event.readInt();
		Integer[] users = new Integer[size];
		for(int i = 0; i < size; i++)
			users[i] = event.readInt();
		Varoke.getSessionManager().sendComposerToUsers(users, new ConsoleInvitationMessageComposer(session.getHabbo().getId(), event.readString()));
	}

}
