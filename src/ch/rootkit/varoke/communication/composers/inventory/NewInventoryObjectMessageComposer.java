package ch.rootkit.varoke.communication.composers.inventory;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class NewInventoryObjectMessageComposer implements MessageComposer {

	final int type;
	final int itemId;
	public NewInventoryObjectMessageComposer(int t, int itmId){
		type = t;
		itemId = itmId;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(1);
		result.writeInt(type);
		result.writeInt(1);
		result.writeInt(itemId);
		return result;
	}

}
