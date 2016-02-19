package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class FavouriteRoomsUpdateMessageComposer implements MessageComposer {

	final int roomId;
	final boolean Add;
	public FavouriteRoomsUpdateMessageComposer(int rId, boolean add){
		roomId = rId;
		Add = add;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(roomId);
		result.writeBoolean(Add);
		return result;
	}

}
