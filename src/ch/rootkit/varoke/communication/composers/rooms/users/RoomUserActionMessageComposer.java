package ch.rootkit.varoke.communication.composers.rooms.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RoomUserActionMessageComposer implements MessageComposer {

	final int VirtualId;
	final int Action;
	public RoomUserActionMessageComposer(int virtualId, int action){
		VirtualId = virtualId;
		Action = action;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(VirtualId);
		result.writeInt(Action);
		return result;
	}

}
