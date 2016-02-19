package ch.rootkit.varoke.communication.composers.rooms.users;

import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class SetRoomUserMessageComposer implements MessageComposer {

	final List<RoomUser> usersToSerialize;
	public SetRoomUserMessageComposer(List<RoomUser> users){
		usersToSerialize = users;
	}
	public SetRoomUserMessageComposer(RoomUser user){
		usersToSerialize = new ArrayList<RoomUser>();
		usersToSerialize.add(user);
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(usersToSerialize.size());
		for(RoomUser roomUser : usersToSerialize){
			result.writeInt(roomUser.getClient().getHabbo().getId());
			result.writeString(roomUser.getClient().getHabbo().getUsername());
			result.writeString(roomUser.getClient().getHabbo().getMotto());
			result.writeString(roomUser.getClient().getHabbo().getLook());
			result.writeInt(roomUser.getVirtualId());
			result.writeInt(roomUser.getPosition().getX());
			result.writeInt(roomUser.getPosition().getY());
			result.writeString(roomUser.getZ() +"");
			result.writeInt(0);
			result.writeInt(1);
			result.writeString(roomUser.getClient().getHabbo().getGender().toLowerCase());
			result.writeInt(0); // fav group id
			result.writeInt(0);
			result.writeString(""); // fav group name
			result.writeString("");
			result.writeInt(0); //ACH Points
			result.writeBoolean(false);
		}
		return result;
	}

}
