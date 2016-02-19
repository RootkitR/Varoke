package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.RoomSettingsDataMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomGetSettingsInfoMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new RoomSettingsDataMessageComposer(Varoke.getFactory().getRoomFactory().getRoomData(event.readInt())));
	}

}
