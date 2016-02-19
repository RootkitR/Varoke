package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class UpdateRoomItemMessageComposer implements MessageComposer {

	final RoomItem roomItem;
	public UpdateRoomItemMessageComposer(RoomItem item){
		roomItem = item;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		roomItem.compose(result);
		return result;
	}

}
