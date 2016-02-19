package ch.rootkit.varoke.communication.composers.wardrobe;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.wardrobe.Wardrobe;
import ch.rootkit.varoke.habbohotel.users.wardrobe.WardrobeItem;

public class LoadWardrobeMessageComposer implements MessageComposer {

	final Wardrobe wardrobe;
	public LoadWardrobeMessageComposer(Wardrobe w){
		wardrobe = w;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(0);
		result.writeInt(wardrobe.size());
		for(WardrobeItem item : wardrobe.getItems().values()){
			result.writeInt(item.getSlot());
			result.writeString(item.getLook());
			result.writeString(item.getGender());
		}
		return result;
	}

}
