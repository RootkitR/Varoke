package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;

public class RoomSettingsDataMessageComposer implements MessageComposer {

	final RoomData roomData;
	public RoomSettingsDataMessageComposer(RoomData rd){
		roomData = rd;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(roomData.getId());
		result.writeString(roomData.getName());
		result.writeString(roomData.getDescription());
		result.writeInt(roomData.getState());
		result.writeInt(roomData.getCategory());
		result.writeInt(roomData.getUsersMax());
		result.writeInt(50);
		result.writeInt(roomData.getTags().size()); //tags count
		for(String tag : roomData.getTags())
			result.writeString(tag);
		result.writeInt(0); //trade
		result.writeInt(roomData.petsAllowed() ? 1 : 0);
		result.writeInt(roomData.petsCanEat() ? 1 : 0);
		result.writeInt(0); //allow walkthrough
		result.writeInt(0); //walls hidden
		result.writeInt(0); //wallthick
		result.writeInt(0); //floorthick
		result.writeInt(0); //chat type
		result.writeInt(0); //chat balloon
		result.writeInt(0); //chat speed
		result.writeInt(0); //chat max distance
		result.writeInt(0); //chat flood protection
		result.writeBoolean(false);
		result.writeInt(0); //who can mute
		result.writeInt(0); //who can kick
		result.writeInt(0); //who can ban
		return result;
	}

}
