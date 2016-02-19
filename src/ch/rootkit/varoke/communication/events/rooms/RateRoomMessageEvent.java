package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.communication.composers.rooms.RoomRatingMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RateRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int rate = event.readInt();
		RoomData currentRoom = session.getHabbo().getCurrentRoom().getData();
		if(currentRoom.canRate(session.getHabbo().getId()) && rate == 1)
		{	currentRoom.addRating(session.getHabbo().getId());}
		session.sendComposer(new RoomRatingMessageComposer(currentRoom.getScore(), currentRoom.canRate(session.getHabbo().getId())));
	}

}
