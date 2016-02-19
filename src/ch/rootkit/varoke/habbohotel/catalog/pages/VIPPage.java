package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class VIPPage extends Page {

	public VIPPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString(getPage().getLayout());
        message.writeInt(2);
        message.writeString(getPage().getHeadline());
        message.writeString(getPage().getTeaser());
        message.writeInt(0);
	}
}
