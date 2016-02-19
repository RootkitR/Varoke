package ch.rootkit.varoke.communication.composers.rooms.items;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.items.Item;

public class OpenGiftMessageComposer implements MessageComposer {

	final Item item;
	final String ExtraData;
	public OpenGiftMessageComposer(Item i, String extraData){
		item = i;
		ExtraData = extraData;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(item.getType());
		result.writeInt(item.getSpriteId());
		result.writeString(item.getItemName());
		result.writeInt(item.getId());
		result.writeString(item.getType());
		result.writeBoolean(true);
		result.writeString(ExtraData);
		return result;
	}

}
