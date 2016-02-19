package ch.rootkit.varoke.communication.composers.rooms.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class TalkMessageComposer implements MessageComposer {

	final boolean Shout;
	final String Message;
	final int Bubble;
	final int Count;
	RoomUser roomUser;
	public TalkMessageComposer(RoomUser ru, boolean shout, String message, int bubble, int count){
		roomUser = ru;
		Shout = shout;
		Message = message;
		Bubble = bubble;
		Count = count;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(Shout ? "ShoutMessageComposer" : "ChatMessageComposer"));
		result.writeInt(roomUser.getVirtualId());
		result.writeString(Message);
		result.writeInt(getEmoji(Message.toLowerCase()));
		result.writeInt(Bubble);
		result.writeInt(0);
		result.writeInt(Count);
		return result;
	}
	public int getEmoji(String message){
        if (message.contains(":)") || message.contains(":d") || message.contains("=]") || message.contains("=d") || message.contains(":>"))
            return 1;
        if (message.contains(">:(") || message.contains(":@")) return 2;
        if (message.contains(":o")) return 3;
        if (message.contains(":(") || message.contains("=[") || message.contains(":'(") || message.contains("='[") || message.contains(":c")) return 4;
        return 0;
    }
}
