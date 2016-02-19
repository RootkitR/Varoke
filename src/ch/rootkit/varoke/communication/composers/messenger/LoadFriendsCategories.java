package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class LoadFriendsCategories implements MessageComposer {

	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(2000);
		result.writeInt(300);
		result.writeInt(800);
		result.writeInt(1100);
		result.writeInt(0);
		return result;
	}

}
