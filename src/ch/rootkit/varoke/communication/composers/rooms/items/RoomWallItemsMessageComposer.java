package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class RoomWallItemsMessageComposer implements MessageComposer{

	final Room room;
	public RoomWallItemsMessageComposer(Room r){
		room = r;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(1);
		result.writeInt(room.getData().getOwner());
		result.writeString(room.getData().getOwnerName());
		result.writeInt(room.getItemManager().getWallItems().size());
		for(RoomItem item : room.getItemManager().getWallItems()){
			item.compose(result);
		}
		return result;
	}

}
