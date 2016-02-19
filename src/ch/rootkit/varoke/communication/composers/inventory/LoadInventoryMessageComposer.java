package ch.rootkit.varoke.communication.composers.inventory;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.inventory.Inventory;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;

public class LoadInventoryMessageComposer implements MessageComposer {

	final Inventory inventory;
	public LoadInventoryMessageComposer(Inventory i){
		inventory = i;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(1);
		result.writeInt(0);
		result.writeInt(inventory.getItems().size());
		for(InventoryItem item : inventory.getItems().values()){
			item.compose(result);
		}
		return result;
	}

}
