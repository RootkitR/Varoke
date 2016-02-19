package ch.rootkit.varoke.communication.events.navigator;

import java.util.List;
import java.util.Random;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.navigator.RoomForwardMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GoToRoomByNameMessageEvent implements MessageEvent{

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		switch(event.readString()){
		case "random_friending_room":
			List<Room> roomArray = Varoke.getGame().getNavigator().getRoomsByFlatcat(9);
			if(roomArray.size() > 0){
				int random = new Random().nextInt(roomArray.size());
				session.sendComposer(new RoomForwardMessageComposer(((Room)roomArray.toArray()[random]).getId()));
			}
			roomArray.clear();
			roomArray = null;
		}
	}

}
