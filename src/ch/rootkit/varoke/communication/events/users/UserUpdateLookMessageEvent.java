package ch.rootkit.varoke.communication.events.users;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.users.UpdateAvatarAspectMessageComposer;
import ch.rootkit.varoke.communication.composers.users.UpdateUserDataMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class UserUpdateLookMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		String Gender = event.readString().toUpperCase();
		String Look = event.readString();
		if(!(Gender.equals("M") || Gender.equals("F")))
			return;
		for(char c : Look.toCharArray())
		{
			if(!(Character.isDigit(c) || Character.isLetter(c) || c == '.' || c == '-'))
				return; // only numbers, letters, - and .
		}
		session.getHabbo().setLook(Look);
		session.getHabbo().setGender(Gender);
		Varoke.getFactory().getUserFactory().saveObject(session.getHabbo().getId(), "look", Look);
		Varoke.getFactory().getUserFactory().saveObject(session.getHabbo().getId(), "gender", Gender);
		session.sendComposer(new UpdateAvatarAspectMessageComposer(Look,Gender));
		session.sendComposer(new UpdateUserDataMessageComposer(-1, session.getHabbo()));
		if(session.getHabbo().getCurrentRoom() != null){
			session.getHabbo().getCurrentRoom().sendComposer(new UpdateUserDataMessageComposer(
					session.getHabbo().getCurrentRoom().getRoomUserById(
							session.getHabbo().getId())));
		}
		session.getHabbo().getMessenger().updateMyself();
	}

}
