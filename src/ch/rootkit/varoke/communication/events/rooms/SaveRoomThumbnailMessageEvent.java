package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class SaveRoomThumbnailMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		/*int length = event.readInt();
		byte[] bytes = event.readBytes(length);
		Inflater decompresser = new Inflater();
	   	decompresser.setInput(bytes, 0, length);
	    byte[] result = new byte[100];
	    int resultLength = decompresser.inflate(result);
	    decompresser.end();TODO CAMERA API !! :D*/
	}

}
