package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.communication.composers.messenger.FriendRequestsMessageComposer;
import ch.rootkit.varoke.communication.composers.messenger.LoadFriendsCategories;
import ch.rootkit.varoke.communication.composers.messenger.LoadFriendsMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class InitMessengerMessageEvent implements MessageEvent {
	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new LoadFriendsCategories());
		session.sendComposer(new LoadFriendsMessageComposer(session.getHabbo().getMessenger()));
		session.sendComposer(new FriendRequestsMessageComposer(session.getHabbo().getMessenger()));
		session.getHabbo().getMessenger().updateMyself();
		session.getHabbo().getMessenger().setInitialized(true);
		session.getHabbo().getMessenger().composeOfflineMessages();
	}

}
