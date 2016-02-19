package ch.rootkit.varoke.communication.composers.rooms.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ApplyHanditemMessageComposer implements MessageComposer {

	final int VirtualId;
	final int CarryItem;
	public ApplyHanditemMessageComposer(int virtualId, int itemId){
		VirtualId = virtualId;
		CarryItem = itemId;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(VirtualId);
		result.writeInt(CarryItem);
		return result;
	}

}
