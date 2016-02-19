package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class LoadRoomRightsListMessageComposer implements MessageComposer {

	final int roomId;
	public LoadRoomRightsListMessageComposer(int id){
		roomId = id;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeInt(roomId);
		message.writeInt(0); //count -> Id, Username
		return message;
	}

}
