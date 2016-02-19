package ch.rootkit.varoke.communication.events.catalog;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.catalog.CatalogPurchaseNotAllowedMessageComposer;
import ch.rootkit.varoke.communication.composers.catalog.GiftErrorMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.exceptions.GiftError;
import ch.rootkit.varoke.habbohotel.exceptions.PurchaseError;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class PurchaseFromCatalogAsGiftMessageEvent implements MessageEvent {
	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		try{
			Varoke.getGame().getCatalog().purchaseAsGift(session, event.readInt(), event.readInt(), event.readString(), event.readString(), event.readString(), event.readInt(), event.readInt(), event.readInt(), event.readBoolean());
		}catch(GiftError giftError){
			session.sendComposer(new GiftErrorMessageComposer());
		}catch(PurchaseError purchaseError){
			session.sendComposer(new CatalogPurchaseNotAllowedMessageComposer(purchaseError.getErrorCode()));
		}
	}
}
