package ch.rootkit.varoke.communication.composers.handshake;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class AuthenticationOKMessageComposer implements MessageComposer {

	@Override
	public ServerMessage compose() throws Exception {
		return new ServerMessage(Outgoing.get(getClass().getSimpleName()));
	}

}
