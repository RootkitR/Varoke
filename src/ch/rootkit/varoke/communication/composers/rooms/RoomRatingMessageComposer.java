package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RoomRatingMessageComposer implements MessageComposer {

	final int Ratings;
	final boolean CanRate;
	public RoomRatingMessageComposer(int ratings, boolean canRate){
		Ratings = ratings;
		CanRate = canRate;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Ratings);
		result.writeBoolean(CanRate);
		return result;
	}

}
