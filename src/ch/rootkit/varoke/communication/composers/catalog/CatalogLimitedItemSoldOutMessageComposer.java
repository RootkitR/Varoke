package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class CatalogLimitedItemSoldOutMessageComposer implements MessageComposer {

	@Override
	public ServerMessage compose() throws Exception {
		return new ServerMessage(Outgoing.get(getClass().getSimpleName()));
	}

}
