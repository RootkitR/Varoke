package ch.rootkit.varoke.communication.composers.users;

import java.util.HashMap;
import java.util.Map.Entry;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class SuperNotificationMessageComposer implements MessageComposer {

	final String text;
	final HashMap<String,String> Map;
	public SuperNotificationMessageComposer(String s, HashMap<String, String> map){
		text = s;
		Map = map;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(text);
		result.writeInt(Map.size());
		for(Entry<String,String> kvp : Map.entrySet()){
			result.writeString(kvp.getKey());
			result.writeString(kvp.getValue());
		}
		return result;
	}

}
