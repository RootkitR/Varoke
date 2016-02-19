package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class ClubGiftsPage extends Page {

	public ClubGiftsPage(CatalogPage p) {
		super(p);
	}
	@Override
	public void compose(ServerMessage message) throws Exception{
		message.writeString("NORMAL");
        message.writeString("club_gifts");
        message.writeInt(1);
        message.writeString(getPage().getHeadline());
        message.writeInt(1);
        message.writeString(getPage().getText1());
	}
}
