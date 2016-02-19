package ch.rootkit.varoke.communication.composers.users;

import java.text.SimpleDateFormat;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class UserProfileMessageComposer implements MessageComposer {

	final Habbo habbo;
	final Session session;
	public UserProfileMessageComposer(Session s, Habbo h){
		habbo = h;
		session = s;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(habbo.getId());
		result.writeString(habbo.getUsername());
		result.writeString(habbo.getLook());
		result.writeString(habbo.getMotto());
		result.writeString(new SimpleDateFormat("dd.MM.yyyy").format(habbo.getAccountCreated()));
		result.writeInt(0); //TODO Achievements points
		result.writeInt(habbo.getMessenger().getFriends().size());
		result.writeBoolean(habbo.getId() != session.getHabbo().getId()
				&& session.getHabbo().getMessenger().getFriends().containsKey(habbo.getId()));
		result.writeBoolean(habbo.getId() != session.getHabbo().getId() &&
				!session.getHabbo().getMessenger().getFriends().containsKey(habbo.getId()) &&
				session.getHabbo().getMessenger().getRequests().containsKey(habbo.getId())
				);
		result.writeBoolean(Varoke.getSessionManager().getSessionByUserId(habbo.getId()) != null);
		result.writeInt(0); //TODO groups (id,name,badge,color1,color2,fav,-1,hasforum)
		result.writeInt((int)(Varoke.getCurrentTimestamp() - habbo.getLastLogin()));
		result.writeBoolean(true);
		return result;
	}

}
