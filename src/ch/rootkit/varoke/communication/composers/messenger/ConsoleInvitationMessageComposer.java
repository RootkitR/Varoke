package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ConsoleInvitationMessageComposer implements MessageComposer {

	final String message;
	final int userId;
	public ConsoleInvitationMessageComposer(int user, String msg){
		userId = user;
		message = msg;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(userId);
		result.writeString(message);
		return result;
	}

}
