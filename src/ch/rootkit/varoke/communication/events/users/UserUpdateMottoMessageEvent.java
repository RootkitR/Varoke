package ch.rootkit.varoke.communication.events.users;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.users.UpdateUserDataMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.chat.SafeChat;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class UserUpdateMottoMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		String motto = event.readString();
		if(!SafeChat.isSafe(motto))
			return;
		motto = SafeChat.filter(motto);
		session.getHabbo().setMotto(motto);
		Varoke.getFactory().getUserFactory().saveObject(session.getHabbo().getId(), "motto", motto);
		if(session.getHabbo().getCurrentRoom() != null){
			session.getHabbo().getCurrentRoom().sendComposer(
					new UpdateUserDataMessageComposer(
							session.getHabbo().getCurrentRoom().getRoomUserById(
									session.getHabbo().getId())));
		}
	}
}
