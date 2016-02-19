package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RoomSpacesMessageComposer implements MessageComposer{

	final String Key;
	final String Value;
	public RoomSpacesMessageComposer(String key, String value){
		Key = key;
		Value = value;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeString(Key);
		message.writeString(Value);
		return message;
	}

}
