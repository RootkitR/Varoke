package ch.rootkit.varoke.communication.events.handshake;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.handshake.SecretKeyMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GenerateSecretKeyMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) {
		String plainKey = Varoke.getRSA().decrypt(event.readString()).replace(Character.toString((char)0),"");
		session.sendComposer(new SecretKeyMessageComposer(
				Varoke.getRSA().sign(session.getDiffieHellman().getPublicKey().toString()).toUpperCase()
				,false)); // maybe if you have time or want Server-Client Crypto, You can do the Server->Client encryption :D
		session.getDiffieHellman().generateSharedKey(plainKey);
		session.enableRC4();
	}

}
