package ch.rootkit.varoke.communication.composers;

import ch.rootkit.varoke.communication.messages.ServerMessage;

public interface MessageComposer {
	public ServerMessage compose() throws Exception;
}
