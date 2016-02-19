package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class FollowFriendErrorMessageComposer implements MessageComposer {

	final int errorCode;
	public FollowFriendErrorMessageComposer(int code){
		errorCode = code;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(errorCode);
		return result;
	}

}
