package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class ThrophiesPage extends Page{

	public ThrophiesPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString("trophies");
        message.writeInt(1);
        message.writeString(getPage().getHeadline());
        message.writeInt(2);
        message.writeString(getPage().getText1());
        message.writeString(getPage().getText2());
	}
}
