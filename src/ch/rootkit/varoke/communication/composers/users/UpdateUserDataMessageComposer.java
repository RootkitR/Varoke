package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class UpdateUserDataMessageComposer implements MessageComposer {

	RoomUser roomUser;
	public UpdateUserDataMessageComposer(RoomUser user){
		roomUser = user;
	}
	Habbo habbo;
	int userId;
	public UpdateUserDataMessageComposer(int userId,Habbo h){
		habbo = h;
	}
	@Override
	public ServerMessage compose() throws Exception {
		ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		if(roomUser != null){
		result.writeInt(roomUser.getVirtualId());
		result.writeString(roomUser.getClient().getHabbo().getLook());
		result.writeString(roomUser.getClient().getHabbo().getGender().toLowerCase());
		result.writeString(roomUser.getClient().getHabbo().getMotto());
		result.writeInt(0); //ACH POINTS
		}else if(habbo != null){
			result.writeInt(userId);
			result.writeString(habbo.getLook());
			result.writeString(habbo.getGender().toLowerCase());
			result.writeString(habbo.getMotto());
			result.writeInt(0); //TODO ACH POINTS
		}
		return result;
	}

}
