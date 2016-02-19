package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class DefaultPage extends Page {

	public DefaultPage(CatalogPage p) {
		super(p);
	}
	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString(getPage().getLayout());
        message.writeInt(3);
        message.writeString(getPage().getHeadline());
        message.writeString(getPage().getTeaser());
        message.writeString(getPage().getPageSpecial());
        message.writeInt(3);
        message.writeString(getPage().getText1());
        message.writeString(getPage().getTextDetails());
        message.writeString(getPage().getTextTeaser());
	}
}
