package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ConsoleChatErrorMessageComposer implements MessageComposer {

	final int errorCode;
	final int conversation;
	public ConsoleChatErrorMessageComposer(int error, int conv){
		errorCode = error;
		conversation = conv;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(errorCode);
		result.writeInt(conversation);
		result.writeString("");
		return result;
	}

}
