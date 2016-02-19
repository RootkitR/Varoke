package ch.rootkit.varoke.communication.events.users;

import ch.rootkit.varoke.communication.composers.users.AchievementPointsMessageComposer;
import ch.rootkit.varoke.communication.composers.users.CitizenshipStatusMessageComposer;
import ch.rootkit.varoke.communication.composers.users.GameCenterGamesListMessageComposer;
import ch.rootkit.varoke.communication.composers.users.SendPerkAllowancesMessageComposer;
import ch.rootkit.varoke.communication.composers.users.UserObjectMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class InfoRetrieveMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		session.sendComposer(new UserObjectMessageComposer(session.getHabbo()));
		session.sendComposer(new SendPerkAllowancesMessageComposer(session.getHabbo()));
		session.sendComposer(new CitizenshipStatusMessageComposer());
		session.sendComposer(new GameCenterGamesListMessageComposer());
		session.sendComposer(new AchievementPointsMessageComposer(100));
		
	}

}
