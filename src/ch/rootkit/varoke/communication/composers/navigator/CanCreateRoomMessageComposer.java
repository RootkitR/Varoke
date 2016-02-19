package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class CanCreateRoomMessageComposer implements MessageComposer {

	final Habbo habbo;
	public CanCreateRoomMessageComposer(Habbo user){
		habbo = user;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Varoke.getFactory().getRoomFactory().readUserRooms(habbo).size() >= 75 ? 1 : 0);
		result.writeInt(75);
		return result;
	}

}
