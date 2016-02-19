package ch.rootkit.varoke.communication.events.inventory;

import ch.rootkit.varoke.communication.composers.inventory.LoadInventoryMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class LoadItemsInventoryMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new LoadInventoryMessageComposer(session.getHabbo().getInventory()));
	}

}
