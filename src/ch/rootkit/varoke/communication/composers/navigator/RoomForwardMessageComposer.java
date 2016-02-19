package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RoomForwardMessageComposer implements MessageComposer {

	final int RoomID;
	public RoomForwardMessageComposer(int roomId){
		RoomID = roomId;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(RoomID);
		return result;
	}

}
