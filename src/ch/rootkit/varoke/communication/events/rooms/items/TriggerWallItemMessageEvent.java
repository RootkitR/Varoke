package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class TriggerWallItemMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		RoomItem roomItem = session.getHabbo().getCurrentRoom().getItemManager().getItem(event.readInt());
		RoomUser roomUser = session.getHabbo().getCurrentRoom().getRoomUserById(
				session.getHabbo().getId());
		if(roomItem == null || roomUser == null)
			return;
		roomItem.getInteractor().onTrigger(roomUser, true);
	}

}
