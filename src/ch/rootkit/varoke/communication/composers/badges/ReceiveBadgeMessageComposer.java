package ch.rootkit.varoke.communication.composers.badges;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ReceiveBadgeMessageComposer implements MessageComposer {

	final String Badge;
	
	public ReceiveBadgeMessageComposer(String badge){
		Badge = badge;
	}
	
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(1);
		result.writeString(Badge);
		return result;
	}

}
