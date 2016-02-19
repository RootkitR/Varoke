package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class GuildCustomPage extends Page{

	public GuildCustomPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString("guild_custom_furni");
        message.writeInt(3);
        message.writeString(getPage().getHeadline());
        message.writeString("");
        message.writeString("");
        message.writeInt(3);
        message.writeString(getPage().getText1());
        message.writeString(getPage().getTextDetails());
        message.writeString(getPage().getText2());
	}
}
