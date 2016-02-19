package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class DeleteFriendMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int size = event.readInt();
		for(int i = 0; i < size; i++){
			int user = event.readInt();
			session.getHabbo().getMessenger().removeFriend(user, true);
		}
	}

}
