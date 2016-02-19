package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class PublishShopMessageComposer implements MessageComposer {
	@Override
	public ServerMessage compose() throws Exception {
		ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeBoolean(true);
		result.writeString("furnidata.xml");
		return result;
	}

}
