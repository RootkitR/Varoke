package ch.rootkit.varoke.communication.composers.rooms.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class UpdateUserStatusMessageComposer implements MessageComposer{

	final List<RoomUser> usersToUpdate;
	public UpdateUserStatusMessageComposer(RoomUser roomUser){
		usersToUpdate = new ArrayList<RoomUser>();
		usersToUpdate.add(roomUser);
	}
	public UpdateUserStatusMessageComposer(List<RoomUser> users){
		usersToUpdate = users;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(usersToUpdate.size());
		for(RoomUser roomUser : usersToUpdate){
			result.writeInt(roomUser.getVirtualId());
			result.writeInt(roomUser.getPosition().getX());
			result.writeInt(roomUser.getPosition().getY());
			result.writeString(roomUser.getZ() +"");
			result.writeInt(roomUser.getRotation());
			result.writeInt(roomUser.getRotation());
			StringBuilder sb = new StringBuilder();
			sb.append("/");
			for(Entry<String, String> status : roomUser.getStatusses().entrySet()){
				sb.append(status.getKey());
				if(!StringUtils.isNullOrEmpty(status.getValue())){
					sb.append(" ");
					sb.append(status.getValue());
				}
				sb.append("/");
			}
			result.writeString(sb.toString());
		}
		return result;
	}

}
