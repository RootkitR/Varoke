package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class ActivityPointsNotificationMessageComposer implements MessageComposer {

	final int Balance;
	final int Change;
	final int Currency;
	public ActivityPointsNotificationMessageComposer(int balance, int change, int currency){
		Balance = balance;
		Change = change;
		Currency = currency;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Balance);
		result.writeInt(Change);
		result.writeInt(Currency);
		return result;
	}

}
