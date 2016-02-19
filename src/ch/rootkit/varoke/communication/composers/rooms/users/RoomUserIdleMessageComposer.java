package ch.rootkit.varoke.communication.composers.rooms.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RoomUserIdleMessageComposer implements MessageComposer {

	final int VirtualId;
	final boolean Idle;
	public RoomUserIdleMessageComposer(int virtualId, boolean idle){
		VirtualId = virtualId;
		Idle = idle;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(VirtualId);
		result.writeBoolean(Idle);
		return result;
	}

}
