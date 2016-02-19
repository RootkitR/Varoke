package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.Preferences;

public class NewNavigatorSizeMessageComposer implements MessageComposer {

	final Preferences preferences;
	public NewNavigatorSizeMessageComposer(Preferences pref){
		preferences = pref;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(preferences.getNavigatorX());
		result.writeInt(preferences.getNavigatorY());
		result.writeInt(preferences.getNavigatorHeight());
		result.writeInt(preferences.getNavigatorWidth());
		result.writeBoolean(false);
		result.writeInt(1);
		return result;
	}

}
