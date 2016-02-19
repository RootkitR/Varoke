package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.navigator.OnCreateRoomInfoMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class CreateRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		if(Varoke.getFactory().getRoomFactory().readUserRooms(session.getHabbo()).size() >= 75){
			session.sendNotif("You can't create any rooms because you already own more than 75 rooms.");
		}
		String name = event.readString();
		String description = event.readString();
		String model = event.readString();
		int category = event.readInt();
		int usersMax = event.readInt();
		int trading = event.readInt();
		int roomId = Varoke.getFactory().getRoomFactory().createRoom(name, description, model, category, usersMax, trading, session.getHabbo());
		session.sendComposer(new OnCreateRoomInfoMessageComposer(roomId, name));
	}

}
