package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class GuildForumPage extends Page{
	public GuildForumPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString("guild_forum");
        message.writeInt(0);
        message.writeInt(2);
        message.writeString(getPage().getText1());
        message.writeString(getPage().getText2());
	}
}
