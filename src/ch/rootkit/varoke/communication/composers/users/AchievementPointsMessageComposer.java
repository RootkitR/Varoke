package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class AchievementPointsMessageComposer implements MessageComposer {

	final int Points;
	public AchievementPointsMessageComposer(int p){
		Points = p;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Points);
		return result;
	}

}
