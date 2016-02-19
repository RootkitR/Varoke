package ch.rootkit.varoke.communication.events.messenger;

import ch.rootkit.varoke.communication.composers.messenger.FollowFriendErrorMessageComposer;
import ch.rootkit.varoke.communication.composers.navigator.RoomForwardMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.messenger.Friend;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class FollowFriendMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		Friend f = session.getHabbo().getMessenger().getFriends().get(event.readInt());
		if(f != null && f.getSession() != null && !f.hideInRoom() && f.getSession().getHabbo().getCurrentRoom() != null)
			session.sendComposer(new RoomForwardMessageComposer(f.getSession().getHabbo().getCurrentRoomId()));
		else
			session.sendComposer(new FollowFriendErrorMessageComposer(2));
	}

}
