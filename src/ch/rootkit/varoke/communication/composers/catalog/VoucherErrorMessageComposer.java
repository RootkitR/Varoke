package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class VoucherErrorMessageComposer implements MessageComposer {

	final int ErrorCode;
	public VoucherErrorMessageComposer(int errorCode){
		ErrorCode = errorCode;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(ErrorCode + "");
		return result;
	}

}
