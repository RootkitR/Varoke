package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;

public class InitialRoomInfoMessageComposer implements MessageComposer {

	final Room room;
	public InitialRoomInfoMessageComposer(Room r){
		room = r;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeString(room.getData().getModelName());
		message.writeInt(room.getData().getId());
		return message;
	}

}
