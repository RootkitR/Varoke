package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class SendPerkAllowancesMessageComposer implements MessageComposer {

	final Habbo habbo;
	public SendPerkAllowancesMessageComposer(Habbo h){
		habbo = h;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(11);
		result.writeString("BUILDERS_AT_WORK");
		result.writeString("");
		result.writeBoolean(true);//can use floor editor?
		result.writeString("VOTE_IN_COMPETITIONS");
		result.writeString("requirement.unfulfilled.helper_level_2");
		result.writeBoolean(false);
		result.writeString("USE_GUIDE_TOOL");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("JUDGE_CHAT_REVIEWS");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("NAVIGATOR_ROOM_THUMBNAIL_CAMERA");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("CALL_ON_HELPERS");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("CITIZEN");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("MOUSE_ZOOM");
		result.writeString("");
		result.writeBoolean(false);
		result.writeString("TRADE");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("CAMERA");
		result.writeString("");
		result.writeBoolean(true);
		result.writeString("NAVIGATOR_PHASE_TWO_2014");
		result.writeString("");
		result.writeBoolean(true);
		return result;
	}

}
