package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class AcceptFriendMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int size = event.readInt();
		for(int i = 0; i < size; i++){
			int userId = event.readInt();
			if(!session.getHabbo().getMessenger().getRequests().containsKey(userId))
				continue;
			if(!session.getHabbo().getMessenger().getFriends().containsKey(userId))
				session.getHabbo().getMessenger().acceptRequest(userId);
		}
	}

}
