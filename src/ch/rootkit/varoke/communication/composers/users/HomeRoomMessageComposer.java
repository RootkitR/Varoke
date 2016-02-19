package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class HomeRoomMessageComposer implements MessageComposer {

	final int RoomId;
	public HomeRoomMessageComposer(int roomId){
		RoomId = roomId;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeInt(RoomId);
		message.writeInt(RoomId);
		return message;
	}

}
