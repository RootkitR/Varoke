package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class GameCenterGamesListMessageComposer implements MessageComposer{

	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeInt(1);
		message.writeInt(18);
		message.writeString("elisa_habbo_stories");
		message.writeString("000000");
		message.writeString("ffffff");
		message.writeString("");
		message.writeString("");
		return message;
	}

}
