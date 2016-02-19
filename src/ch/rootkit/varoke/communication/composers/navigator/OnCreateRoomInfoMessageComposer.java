package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class OnCreateRoomInfoMessageComposer implements MessageComposer {

	final int Id;
	final String Name;
	public OnCreateRoomInfoMessageComposer(int id, String name){
		Id = id;
		Name = name;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeInt(Id);
		message.writeString(Name);
		return message;
	}

}
