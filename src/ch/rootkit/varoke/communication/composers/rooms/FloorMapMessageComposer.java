package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;

public class FloorMapMessageComposer implements MessageComposer {

	final Room room;
	public FloorMapMessageComposer(Room r){
		room = r;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeBoolean(true);
		result.writeInt(-1); //wallheight
		result.writeString(room.getModel().getRelativeMap());
		return result;
	}

}
