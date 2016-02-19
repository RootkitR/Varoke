package ch.rootkit.varoke.communication.events.wardrobe;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.wardrobe.WardrobeItem;

public class WardrobeUpdateMessageEvent implements MessageEvent {
	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int Slot = event.readInt();
		String Look = event.readString();
		String Gender = event.readString();
		if(!(Gender.equals("M") || Gender.equals("F")))
			return;
		for(char c : Look.toCharArray())
		{
			if(!(Character.isDigit(c) || Character.isLetter(c) || c == '.' || c == '-'))
				return; // only numbers, letters, - and .
		}
		session.getHabbo().getWardrobe().removeItem(Slot);
		session.getHabbo().getWardrobe().addItem(new WardrobeItem(Slot,Look, Gender));
		Varoke.getFactory().getWardrobeFactory().save(Slot,Look,Gender, session.getHabbo().getId());
	}
}
