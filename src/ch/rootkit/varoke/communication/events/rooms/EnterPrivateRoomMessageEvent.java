package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.InitialRoomInfoMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.RoomSpacesMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class EnterPrivateRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int id = event.readInt();
		if(session.getHabbo().getCurrentRoomId() > 0)
			session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId()).removeFromRoom();
		session.getHabbo().setLoadingRoom(id);
		Room r = Varoke.getGame().getRoomManager().getRoom(id);
		session.sendMessage(new ServerMessage(Outgoing.get("PrepareRoomMessageComposer")));
		ServerMessage message = new ServerMessage(Outgoing.get("RoomGroupMessageComposer"));
		message.writeInt(0);
		session.sendMessage(message);
		session.sendComposer(new InitialRoomInfoMessageComposer(r));
		if(!r.getData().getWallpaper().equals("0.0"))
			session.sendComposer(new RoomSpacesMessageComposer("wallpaper", r.getData().getWallpaper()));
		if(!r.getData().getFloor().equals("0.0"))
			session.sendComposer(new RoomSpacesMessageComposer("floor", r.getData().getFloor()));
		session.sendComposer(new RoomSpacesMessageComposer("landscape", r.getData().getLandscape()));
		
		message = new ServerMessage(Outgoing.get("RoomRightsLevelMessageComposer"));
		message.writeInt(4);
		session.sendMessage(message);
		session.sendMessage(new ServerMessage(Outgoing.get("HasOwnerRightsMessageComposer")));
		message = new ServerMessage(Outgoing.get("RoomRatingMessageComposer"));
		message.writeInt(r.getData().getScore());
		message.writeBoolean(true); //canvote
		session.sendMessage(message);
		message = new ServerMessage(Outgoing.get("EnableRoomInfoMessageComposer"));
		message.writeInt(id);
		session.sendMessage(message);
		message = null;
	}

}
