package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class RecyclerPrizesPage extends Page{

	public RecyclerPrizesPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception{
		message.writeString("NORMAL");
        message.writeString("recycler_prizes");
        message.writeInt(1);
        message.writeString("catalog_recycler_headline3");
        message.writeInt(1);
        message.writeString(getPage().getText1());
	}
}
