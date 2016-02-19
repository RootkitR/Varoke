package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class VoucherValidMessageComposer implements MessageComposer {
	
	final String FurniName;
	final String FurniDescription;
	public VoucherValidMessageComposer(String furniName, String furniDescription){
		FurniName = furniName;
		FurniDescription = furniDescription;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(FurniName);
		result.writeString(FurniDescription);
		return result;
	}

}
