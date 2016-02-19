package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;

public class HeightMapMessageComposer implements MessageComposer{

	final Room room;
	public HeightMapMessageComposer(Room r){
		room = r;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(room.getModel().getSizeX());
		result.writeInt(room.getModel().getSizeX() * room.getModel().getSizeY());
		for(int y = 0; y < room.getModel().getSizeY(); y++)
			for(int x = 0; x < room.getModel().getSizeX(); x++)
				result.writeShort((short)(room.getItemManager().getHeight(x, y) * 256));
		return result;
	}

}
