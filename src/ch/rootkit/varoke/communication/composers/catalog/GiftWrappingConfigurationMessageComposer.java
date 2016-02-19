package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class GiftWrappingConfigurationMessageComposer implements MessageComposer {

	
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeBoolean(true);
		result.writeInt(1);
		result.writeInt(Varoke.getGame().getCatalog().getNewWrapping().size());
		for(int i : Varoke.getGame().getCatalog().getNewWrapping())
			result.writeInt(i);
		result.writeInt(8);
		for(int i = 0; i != 8; i++)
			result.writeInt(i);
		result.writeInt(11);
		for(int i = 0; i != 11; i++)
			result.writeInt(i);
		result.writeInt(Varoke.getGame().getCatalog().getOldWrapping().size());
		for(int i : Varoke.getGame().getCatalog().getOldWrapping())
			result.writeInt(i);
		return result;
	}

}
