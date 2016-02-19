package ch.rootkit.varoke.communication.composers.catalog;

import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class CatalogueIndexMessageComposer implements MessageComposer {

	final String Type;
	final Session session;
	public CatalogueIndexMessageComposer(String type, Session s){
		Type = type;
		session = s;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeBoolean(true);
		result.writeInt(0);
		result.writeInt(-1);
		result.writeString("root");
		result.writeString("");
		result.writeInt(0);
		final List<CatalogPage> pages = Varoke.getGame().getCatalog().getPagesByRankAndParent(session.getHabbo().getRank(), -1);
		result.writeInt(pages.size());
		for(CatalogPage p : pages)
		{
			result.writeBoolean(p.isVisible());
			result.writeInt(p.getIcon());
			result.writeInt(p.getId());
			result.writeString(p.getCodeName());
			result.writeString(p.getCaption());
			result.writeInt(p.getFlatOffers().size());
			for(int i : p.getFlatOffers()){
				result.writeInt(i);
			}
			final List<CatalogPage> subPages = Varoke.getGame().getCatalog().getPagesByRankAndParent(session.getHabbo().getRank(), p.getId());
			result.writeInt(subPages.size());
			for(CatalogPage subPage : subPages)
			{
				result.writeBoolean(subPage.isVisible());
				result.writeInt(subPage.getIcon());
				result.writeInt(subPage.getId());
				result.writeString(subPage.getCodeName());
				result.writeString(subPage.getCaption());
				result.writeInt(subPage.getFlatOffers().size());
				for(int i : subPage.getFlatOffers()){
					result.writeInt(i);
				}
				result.writeInt(0);
			}
			subPages.clear();
		}
		pages.clear();
		result.writeBoolean(false);
        result.writeString(Type);
		return result;
	}

}
