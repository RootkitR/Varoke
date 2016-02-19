package ch.rootkit.varoke.communication.composers.messenger;

import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.messenger.Result;

public class ConsoleSearchFriendMessageComposer implements MessageComposer {

	final List<Result> Friends;
	final List<Result> Strangers;
	public ConsoleSearchFriendMessageComposer(List<Result> friends, List<Result> strangers){
		Friends = friends;
		Strangers = strangers;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Friends.size());
		for(Result r : Friends)
			r.compose(result);
		Friends.clear();
		result.writeInt(Strangers.size());
		for(Result r : Strangers)
			r.compose(result);
		Strangers.clear();
		return result;
	}

}
