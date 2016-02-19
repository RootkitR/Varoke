package ch.rootkit.varoke.communication.composers.handshake;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class SecretKeyMessageComposer implements MessageComposer {

	final String SecretKey;
	final boolean CryptoToClient;
	public SecretKeyMessageComposer(String key, boolean toClientEncryption){
		SecretKey = key;
		CryptoToClient = toClientEncryption;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage message = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		message.writeString(SecretKey);
		message.writeBoolean(CryptoToClient);
		return message;
	}

}
