package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.messenger.Friend;
import ch.rootkit.varoke.habbohotel.messenger.Messenger;

public class LoadFriendsMessageComposer implements MessageComposer {

	final Messenger messenger;
	public LoadFriendsMessageComposer(Messenger m){
		messenger = m;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(0);
		result.writeInt(1);
		result.writeInt(messenger.getFriends().size());
		for(Friend f : messenger.getFriends().values()){
			f.compose(result);
		}
		return result;
	}

}
