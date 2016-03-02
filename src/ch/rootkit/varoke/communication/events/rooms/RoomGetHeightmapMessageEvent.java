package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomGetHeightmapMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) {
		try{
			Varoke.getGame().getRoomManager().enterRoom(session);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
