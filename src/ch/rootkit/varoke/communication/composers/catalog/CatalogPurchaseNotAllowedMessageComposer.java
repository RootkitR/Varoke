package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class CatalogPurchaseNotAllowedMessageComposer implements MessageComposer {

	final int ErrorCode;
	public CatalogPurchaseNotAllowedMessageComposer(int error){
		ErrorCode = error;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(ErrorCode);
		return result;
	}

}
