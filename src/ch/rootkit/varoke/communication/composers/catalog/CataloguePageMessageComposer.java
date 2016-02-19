package ch.rootkit.varoke.communication.composers.catalog;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogItem;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class CataloguePageMessageComposer implements MessageComposer {

	final CatalogPage page;
	final Session session;
	public CataloguePageMessageComposer(CatalogPage p, Session s){
		page = p;
		session = s;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(page.getId());
		page.compose(result);
		if(page.getLayout().startsWith("frontpage") || page.getLayout().equals("vip_buy") || page.getLayout().equals("recycler"))
			result.writeInt(0);
		else{
			result.writeInt(page.getItems().size());
			for(CatalogItem item : page.getItems())
				item.compose(result);
		}
		result.writeInt(-1);
		result.writeBoolean(false);
		return result;
	}

}
