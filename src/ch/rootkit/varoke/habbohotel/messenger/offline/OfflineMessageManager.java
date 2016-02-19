package ch.rootkit.varoke.habbohotel.messenger.offline;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Logger;

public class OfflineMessageManager {
	
	private List<OfflineMessage> messages;
	public OfflineMessageManager() throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Loading Offline Messages ");
		messages = Varoke.getFactory().getMessengerFactory().readOfflineMessages();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	public List<OfflineMessage> getMessagesByUser(int userId){
		List<OfflineMessage> result = new ArrayList<OfflineMessage>();
		for(OfflineMessage om : messages){
			if(om.getToId() == userId)
				result.add(om);
		}
		return result;
	}
	public void storeMessage(int from, int to, String message) throws Exception{
		OfflineMessage msg = new OfflineMessage(from,to,message, Varoke.getCurrentTimestamp());
		messages.add(msg);
		Varoke.getFactory().getMessengerFactory().storeOffline(msg);
	}
	public void removeMessage(OfflineMessage message) throws Exception{
		messages.remove(message);
		Varoke.getFactory().getMessengerFactory().removeOfflineMessage(message);
	}
}
