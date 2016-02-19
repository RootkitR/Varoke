package ch.rootkit.varoke.communication.events.handshake;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.handshake.InitCryptoMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class InitCryptoMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) {
		session.sendComposer(new InitCryptoMessageComposer(
				Varoke.getRSA().sign(session.getDiffieHellman().getPrime().toString()).toUpperCase(),
				Varoke.getRSA().sign(session.getDiffieHellman().getGenerator().toString()).toUpperCase()));
	}

}
