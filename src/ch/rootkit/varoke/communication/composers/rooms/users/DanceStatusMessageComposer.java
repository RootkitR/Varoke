package ch.rootkit.varoke.communication.composers.rooms.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class DanceStatusMessageComposer implements MessageComposer {

	final int VirtualId;
	final int Dance;
	public DanceStatusMessageComposer(int virtualId, int dance){
		VirtualId = virtualId;
		Dance = dance;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(VirtualId);
		result.writeInt(Dance);
		return result;
	}

}
