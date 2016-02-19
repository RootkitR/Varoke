package ch.rootkit.varoke.communication.events.navigator;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.Preferences;

public class NewNavigatorResizeEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int x = event.readInt();
		int y = event.readInt();
		int width = event.readInt();
		int height = event.readInt();
		if(session.getHabbo().getPreferences() == null)
		{
			session.getHabbo().setPreferences(new Preferences(
					session.getHabbo().getId(),
					"0,0,0",
					x,y,width,height
					));
			session.getHabbo().getPreferences().save();
		}else{
			session.getHabbo().getPreferences().
			setNavigatorX(x).
			setNavigatorY(y).
			setNavigatorHeight(height).
			setNavigatorWidth(width).
			save();
		}
	}

}
