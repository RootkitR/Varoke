package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.communication.composers.rooms.LoadRoomRightsListMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.RoomDataMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomGetInfoMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int id = event.readInt();
        int num = event.readInt();
        int num2 = event.readInt();
        boolean b = true;
        if (num == 0 && num2 == 1)
            b = false;
        else if (num == 1 && num2 == 0)
        	b = true;
        session.sendComposer(new RoomDataMessageComposer(session, id, b));
        session.sendComposer(new LoadRoomRightsListMessageComposer(id));
	}

}
