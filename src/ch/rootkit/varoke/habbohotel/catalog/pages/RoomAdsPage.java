package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class RoomAdsPage extends Page {

	
	public RoomAdsPage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception{
		 message.writeString("NORMAL");
         message.writeString("roomads");
         message.writeInt(2);
         message.writeString("events_header");
         message.writeString("");
         message.writeInt(2);
         message.writeString(getPage().getText1());
         message.writeString("");
	}
	
}
