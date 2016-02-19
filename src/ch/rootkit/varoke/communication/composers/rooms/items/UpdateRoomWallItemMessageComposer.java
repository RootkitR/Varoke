package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class UpdateRoomWallItemMessageComposer implements MessageComposer {

	final RoomItem roomItem;
	public UpdateRoomWallItemMessageComposer(RoomItem item){
		roomItem = item;
	}
	@Override
	public ServerMessage compose() throws Exception {
		ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		roomItem.compose(result);
		return result;
	}

}
