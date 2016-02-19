package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class NavigatorMetaDataComposer implements MessageComposer {

	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(4);
		result.writeString("official_view");
		result.writeInt(0);
		result.writeString("hotel_view");
		result.writeInt(0);
		result.writeString("roomads_view");
		result.writeInt(0);
		result.writeString("myworld_view");
		result.writeInt(0);
		return result;
	}

}
