package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class PickUpItemMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		event.readInt();
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getItem(event.readInt());
		if(!session.getHabbo().getCurrentRoom().hasRights(session.getHabbo(), true) || item == null)
			return;
		session.getHabbo().getCurrentRoom().getItemManager().removeItem(item.getId(), session.getHabbo().getId());
		session.getHabbo().getInventory().addItem(item.getId(), item.getBaseItemId(), item.getExtraData(), item.getLimitedId(), item.getGroupId(), item.isBuildersFurni());
	}
}
