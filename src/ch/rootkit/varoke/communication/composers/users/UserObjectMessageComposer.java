package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class UserObjectMessageComposer implements MessageComposer {

	final private Habbo habbo;
	public UserObjectMessageComposer(Habbo h){
		habbo = h;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(habbo.getId());
		result.writeString(habbo.getUsername());
		result.writeString(habbo.getLook());
		result.writeString(habbo.getGender().toUpperCase());
		result.writeString(habbo.getMotto());
		result.writeString("");
		result.writeBoolean(false);
		result.writeInt(0); //Respect
		result.writeInt(3); //DailyRespectPoints
		result.writeInt(3); //DailyPetRespectPoints
		result.writeBoolean(true);
		result.writeString(habbo.getLastLogin() +"");
		result.writeBoolean(false); //flagme
		result.writeBoolean(false);
		return result;
	}

}
