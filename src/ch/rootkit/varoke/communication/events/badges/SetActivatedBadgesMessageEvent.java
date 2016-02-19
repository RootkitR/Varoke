package ch.rootkit.varoke.communication.events.badges;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.composers.badges.UserBadgesMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class SetActivatedBadgesMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.getHabbo().getBadgeComponent().resetSlots();
		for (int i = 0; i < 5; i++){
			int slot = event.readInt();
			String code = event.readString();
			if(code.length() == 0) continue;
			if(!session.getHabbo().getBadgeComponent().hasBadge(code) || slot < 1 || slot > 5) return;
			session.getHabbo().getBadgeComponent().getBadge(code).setSlot(slot);
			Varoke.getFactory().getBadgeFactory().updateSlot(code, slot, session.getHabbo().getId());
		}
		MessageComposer composer = new UserBadgesMessageComposer(session.getHabbo().getBadgeComponent());
		if(session.getHabbo().getCurrentRoom() != null){
			session.getHabbo().getCurrentRoom().sendComposer(composer);
			composer = null;
			return;
		}
		session.sendComposer(composer);
	}

}
