package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class DeclineFriendMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		boolean all = event.readBoolean();
		if(all)
			session.getHabbo().getMessenger().declineAllRequests();
		else
			session.getHabbo().getMessenger().declineRequest(event.readInt());
	}

}
