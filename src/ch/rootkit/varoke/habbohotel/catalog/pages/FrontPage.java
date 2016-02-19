package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class FrontPage extends Page{

	public FrontPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception{
		message.writeString("NORMAL");
        message.writeString("frontpage4");
        message.writeInt(2);
        message.writeString(getPage().getHeadline());
        message.writeString(getPage().getTeaser());
        message.writeInt(2);
        message.writeString(getPage().getText1());
        message.writeString(getPage().getText2());
        message.writeInt(0);
        message.writeInt(-1);
        message.writeBoolean(false);
	}
}
