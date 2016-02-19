package ch.rootkit.varoke.communication.composers.inventory;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RemoveInventoryObjectMessageComposer implements MessageComposer {

	final int ItemId;
	public RemoveInventoryObjectMessageComposer(int itemId){
		ItemId = itemId;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(ItemId);
		return result;
	}

}
