package ch.rootkit.varoke.communication.events.handshake;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class CheckReleaseMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		String version = event.readString();
		if(Varoke.Version.equals(version))
			return;
		System.err.println("WARNING! Client is using Version \"" + version + "\" but needs " + Varoke.Version);
	}

}
