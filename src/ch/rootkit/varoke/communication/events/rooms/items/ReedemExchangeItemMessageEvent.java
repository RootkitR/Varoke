package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class ReedemExchangeItemMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getItem(event.readInt());
		if(item == null || !session.getHabbo().getCurrentRoom().hasRights(session.getHabbo(), true) || !item.getBaseItem().getItemName().startsWith("CF"))
			return;
		int worth = Integer.parseInt(item.getBaseItem().getItemName().split("_")[1]);
		session.getHabbo().getCurrentRoom().getItemManager().removeItem(item.getId(), 0);
		Varoke.getFactory().getItemFactory().remove(item.getId());
		session.getHabbo().giveCredits(worth);
	}

}
