package ch.rootkit.varoke.communication.composers.users;

import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class FavouriteRoomsMessageComposer implements MessageComposer {

	final List<Integer> FavRooms;
	public FavouriteRoomsMessageComposer(List<Integer> favRooms){
		FavRooms = favRooms;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(30);
		result.writeInt(FavRooms.size());
		for(int i : FavRooms) result.writeInt(i);
		return result;
	}

}
