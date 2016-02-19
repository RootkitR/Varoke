package ch.rootkit.varoke.communication.composers.users;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class UpdateAvatarAspectMessageComposer implements MessageComposer {

	final String Look;
	final String Gender;
	public UpdateAvatarAspectMessageComposer(String look, String gender){
		Look = look;
		Gender = gender;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(Look);
		result.writeString(Gender);
		return result;
	}

}
