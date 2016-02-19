package ch.rootkit.varoke.communication.composers.rooms.items;

import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.Room;

public class UpdateFurniStackMapMessageComposer implements MessageComposer {

	final List<Point> tiles;
	final Room room;
	public UpdateFurniStackMapMessageComposer(List<Point> t, Room r){
		tiles = t;
		room = r;
	}
	
	public UpdateFurniStackMapMessageComposer(Point p, Room r){
		tiles = new ArrayList<Point>();
		tiles.add(p);
		room = r;
	}
	@Override
	public ServerMessage compose() throws Exception {
		ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(tiles.size());
		for(Point p : tiles){
			result.writeByte(p.getX());
			result.writeByte(p.getY());
			result.writeShort((short)(room.getItemManager().getHeight(p.getX(), p.getY()) * 256));
		}
		tiles.clear();
		return result;
	}

}
