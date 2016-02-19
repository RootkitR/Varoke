package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.messenger.Request;

public class ConsoleSendFriendRequestMessageComposer implements MessageComposer{

	final Request request;
	public ConsoleSendFriendRequestMessageComposer(Request r){
		request = r;
	}
	public ServerMessage compose() throws Exception{
		final ServerMessage result = new ServerMessage(Outgoing.get("ConsoleSendFriendRequestMessageComposer"));
		request.compose(result);
		return result;
	}
}
