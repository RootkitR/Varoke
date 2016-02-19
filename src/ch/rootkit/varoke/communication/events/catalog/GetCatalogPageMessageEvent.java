package ch.rootkit.varoke.communication.events.catalog;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.catalog.CataloguePageMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class GetCatalogPageMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		CatalogPage catalogPage = Varoke.getGame().getCatalog().getPage(event.readInt());
		if(catalogPage == null || catalogPage.getMinRank() > session.getHabbo().getRank() || !catalogPage.isEnabled() || !catalogPage.isVisible())
			return;
		session.sendComposer(new CataloguePageMessageComposer(catalogPage, session));
	}

}
