package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class RoomFloorItemsMessageComposer implements MessageComposer{

	final Room room;
	public RoomFloorItemsMessageComposer(Room r){
		room = r;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeInt(1);
		message.writeInt(room.getData().getOwner());
		message.writeString(room.getData().getOwnerName());
		message.writeInt(room.getItemManager().getFloorItems().size());
		for(RoomItem roomItem : room.getItemManager().getFloorItems()){
			roomItem.compose(message);
		}
		return message;
	}

}
