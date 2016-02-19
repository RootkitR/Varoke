package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class NormalBuildersClubAddOnsPage extends Page{
	public NormalBuildersClubAddOnsPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString("builders_club_addons");
        message.writeInt(0);
        message.writeInt(1);
        message.writeString(getPage().getHeadline());
	}
}
