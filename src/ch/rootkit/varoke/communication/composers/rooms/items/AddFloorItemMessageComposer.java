package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;

public class AddFloorItemMessageComposer implements MessageComposer {

	final RoomItem item;
	public AddFloorItemMessageComposer(RoomItem i){
		item = i;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		item.compose(result);
		result.writeString(Varoke.getFactory().getUserFactory().getValueFromUser("username", item.getOwnerId()).toString());
		return result;
	}

}
