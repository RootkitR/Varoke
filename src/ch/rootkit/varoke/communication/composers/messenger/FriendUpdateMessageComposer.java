package ch.rootkit.varoke.communication.composers.messenger;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.messenger.Friend;
import ch.rootkit.varoke.habbohotel.messenger.FriendUpdateType;

public class FriendUpdateMessageComposer implements MessageComposer {

	final FriendUpdateType type;
	final Friend friend;
	public FriendUpdateMessageComposer(FriendUpdateType t, Friend f){
		type = t;
		friend = f;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(0);
		result.writeInt(1);
		switch(type){
		case REMOVE:
			result.writeInt(-1);
			result.writeInt(friend.getId());
			break;
		case UPDATE:
			result.writeInt(0);
			friend.compose(result);
			result.writeBoolean(false);
			break;
		default:
			break;
		}
		return result;
	}

}
