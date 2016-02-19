package ch.rootkit.varoke.communication.composers.rooms;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomDataMessageComposer implements MessageComposer {

	final Session Session;
	final RoomData data;
	final boolean Show;
	public RoomDataMessageComposer(Session user, int roomId, boolean show) throws Exception{
		Session = user;
		data = Varoke.getFactory().getRoomFactory().getRoomData(roomId);
		Show = show;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeBoolean(Show);
		message.writeInt(data.getId());
        message.writeString(data.getName());
        message.writeInt(data.getOwner());
        message.writeString(data.getOwnerName());
        message.writeInt(data.getState());
        message.writeInt(data.getUsersOnline()); //users now
        message.writeInt(data.getUsersMax());
        message.writeString(data.getDescription());
        message.writeInt(0); // trade state
        message.writeInt(data.getScore());
        message.writeInt(0); // Ranking
        message.writeInt(data.getCategory());
        message.writeInt(data.getTags().size()); //Tag Count
        for(String tag : data.getTags())
        	message.writeString(tag);
        int enumType = Show ? 32 : 0;
        if (data.petsAllowed()) enumType += 16;
        message.writeInt(enumType + 8);
        message.writeBoolean(true);
        message.writeBoolean(false);// staffPick
        message.writeBoolean(false);// bypass bell, pass ...
        message.writeBoolean(data.roomMuted()); //roomMuted
        message.writeInt(0); //who can mute
        message.writeInt(0); //who can kick
        message.writeInt(0); //who can ban
        message.writeBoolean(true); //rights
        message.writeInt(0); //chat type
        message.writeInt(0); //chat balloon
        message.writeInt(0); //chat speed
        message.writeInt(0); //chat max distance
        message.writeInt(0); //chat flood protection
		return message;
	}

}
