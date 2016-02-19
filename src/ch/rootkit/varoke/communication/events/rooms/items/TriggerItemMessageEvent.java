package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class TriggerItemMessageEvent implements MessageEvent {
	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int itemId = event.readInt();
		if(session.getHabbo().getCurrentRoom() == null && session.getHabbo().getCurrentRoom().getItemManager().getItem(itemId) == null)
			return;
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getItem(itemId);
		item.getInteractor().onTrigger(
				session.getHabbo().getCurrentRoom().getRoomUserById(
						session.getHabbo().getId()), true);
	}
}
