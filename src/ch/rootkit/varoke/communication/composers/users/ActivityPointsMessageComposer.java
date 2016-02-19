package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ActivityPointsMessageComposer implements MessageComposer {

	final int Duckets;
	final int Diamonds;
	public ActivityPointsMessageComposer(int duckets, int diamonds){
		Duckets = duckets;
		Diamonds = diamonds;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(2);
		result.writeInt(0);
		result.writeInt(Duckets);
		result.writeInt(5);
		result.writeInt(Diamonds);
		return result;
	}

}
