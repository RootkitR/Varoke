package ch.rootkit.varoke.communication.events.handshake;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.handshake.AuthenticationOKMessageComposer;
import ch.rootkit.varoke.communication.composers.users.ActivityPointsMessageComposer;
import ch.rootkit.varoke.communication.composers.users.CreditsBalanceMessageComposer;
import ch.rootkit.varoke.communication.composers.users.FavouriteRoomsMessageComposer;
import ch.rootkit.varoke.communication.composers.users.HomeRoomMessageComposer;
import ch.rootkit.varoke.communication.composers.users.UserClubRightsMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.utils.Logger;

public class SSOTicketMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		Habbo h = Varoke.getFactory().getUserFactory().createHabbo(event.readString());
		if(h == null || Varoke.getSessionManager().getSessionByUserId(h.getId()) != null){ session.close(); return;}
		session.setPassedHandshake(true);
		Varoke.getFactory().getUserFactory().updateValue(h.getId(), "last_login", h.getLastLogin());
		session.setHabbo(h);
		session.sendComposer(new AuthenticationOKMessageComposer());
		session.sendComposer(new HomeRoomMessageComposer(h.getHome()));
		session.sendComposer(new FavouriteRoomsMessageComposer(h.getFavorites()));
		session.sendComposer(new UserClubRightsMessageComposer(h.getRank()));
		session.sendComposer(new ActivityPointsMessageComposer(h.getDuckets(), h.getDiamonds()));
		session.sendComposer(new CreditsBalanceMessageComposer(h.getCredits()));
		Logger.printVarokeLine(h.getUsername() + " has connected (" + session.getChannel().remoteAddress().toString() + ")");
	}

}
