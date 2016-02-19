package ch.rootkit.varoke.communication.composers.handshake;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class InitCryptoMessageComposer implements MessageComposer {
	final String Prime;
	final String Generator;
	public InitCryptoMessageComposer(String prime, String generator){
		Prime = prime;
		Generator = generator;
	}
	@Override
	public ServerMessage compose() throws Exception{
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeString(Prime);
		message.writeString(Generator);
		return message;
	}

}
