package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class PickUpWallItemMessageComposer implements MessageComposer {

	final RoomItem roomItem;
	final int pickerId;
	public PickUpWallItemMessageComposer(RoomItem item, int picker){
		roomItem = item;
		pickerId = picker;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(roomItem.getId() + "");
		result.writeInt(pickerId);
		return result;
	}

}
