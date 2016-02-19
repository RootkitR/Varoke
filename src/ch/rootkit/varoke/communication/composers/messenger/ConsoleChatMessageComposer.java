package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ConsoleChatMessageComposer implements MessageComposer {

	final int From;
	final String Message;
	final long Time;
	public ConsoleChatMessageComposer(int from, String message, long time){
		From = from;
		Message = message;
		Time = time;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(From);
		result.writeString(Message);
		result.writeLong(Time);
		return result;
	}

}
