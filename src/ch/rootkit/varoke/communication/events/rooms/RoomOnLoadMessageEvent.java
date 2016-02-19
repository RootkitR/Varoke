package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomOnLoadMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		ServerMessage message = new ServerMessage(Outgoing.get("SendRoomCampaignFurnitureMessageComposer"));
		message.writeInt(0);
		session.sendMessage(message);
		message = null;
	}

}
