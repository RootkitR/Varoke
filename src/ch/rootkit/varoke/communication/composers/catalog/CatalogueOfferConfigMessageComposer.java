package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class CatalogueOfferConfigMessageComposer implements MessageComposer {

	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(100);
        result.writeInt(6);
        result.writeInt(1);
        result.writeInt(1);
        result.writeInt(2);
        result.writeInt(40);
        result.writeInt(99);
        return result;
	}

}
