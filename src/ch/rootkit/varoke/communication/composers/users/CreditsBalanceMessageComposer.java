package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class CreditsBalanceMessageComposer implements MessageComposer {

	final int Credits;
	public CreditsBalanceMessageComposer(int credits){
		Credits = credits;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(Credits + ".0");
		return result;
	}

}
