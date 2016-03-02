package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class TriggerDiceCloseMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getItem(event.readInt());
		if(item == null || !item.getBaseItem().getInteractionType().equals("dice"))
			return;
		item.setExtraData("0");
		item.update();
		
	}

}
