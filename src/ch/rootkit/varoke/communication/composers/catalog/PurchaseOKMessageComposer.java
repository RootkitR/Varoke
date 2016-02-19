package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class PurchaseOKMessageComposer implements MessageComposer {

	final Session session;
	final CatalogItem item;
	public PurchaseOKMessageComposer(Session s, CatalogItem i) {
		session = s;
		item = i;
	}

	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		item.compose(result);
		return result;
	}

}
