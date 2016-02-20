package ch.rootkit.varoke.communication.events.rooms.users;

import ch.rootkit.varoke.communication.composers.rooms.users.TalkMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.headers.Incoming;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.chat.CommandHandler;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class TalkMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		boolean shout = event.Id == Incoming.get("ShoutMessageEvent");
		String message = event.readString();
		int bubble = event.readInt();
		if(message.startsWith(":") && CommandHandler.handled(session, message))
			return;
		if(session.getHabbo().isMuted())
			return;
		session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId()).setIdle(false);
		session.getHabbo().getCurrentRoom().sendComposer(new TalkMessageComposer(
				session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId()),
				shout, message, bubble == 1 ? 0 : bubble, shout ? -1 : event.readInt()));
		CommandHandler.storeMessage(session.getHabbo().getId(), -1, session.getHabbo().getCurrentRoomId(), message, "default");
	}

}
