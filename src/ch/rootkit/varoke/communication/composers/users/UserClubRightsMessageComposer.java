package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class UserClubRightsMessageComposer implements MessageComposer {

	final int Rank;
	public UserClubRightsMessageComposer(int rank){
		Rank = rank;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(2);
		result.writeInt(Rank);
		result.writeInt(0);
		return result;
	}

}
