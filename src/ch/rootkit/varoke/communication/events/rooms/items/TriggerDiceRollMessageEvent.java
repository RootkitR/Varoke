package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class TriggerDiceRollMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getItem(event.readInt());
		if(item == null || !item.getBaseItem().getInteractionType().equals("dice"))
			return;
		item.getInteractor().onTrigger(
				session.getHabbo().getCurrentRoom().getRoomUserById(
						session.getHabbo().getId()),
				false);
	}

}
