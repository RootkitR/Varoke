package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class WallItemMoveMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		if(!session.getHabbo().getCurrentRoom().hasRights(session.getHabbo(), false))
			return;
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getFloorItem(Math.abs(event.readInt()));
		if(item == null)
			return;
		item.setExtraData(":" + event.readString().split(":")[1]);
		item.update();
	}

}
