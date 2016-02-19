package ch.rootkit.varoke.communication.events.catalog;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.catalog.CatalogPurchaseNotAllowedMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.exceptions.PurchaseError;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class PurchaseFromCatalogMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		try{
		Varoke.getGame().getCatalog().purchase(event.readInt(), event.readInt(), event.readString(), event.readInt(), session);
		}catch (PurchaseError purchaseError){
			session.sendComposer(new CatalogPurchaseNotAllowedMessageComposer(purchaseError.getErrorCode()));
		}
	}

}
