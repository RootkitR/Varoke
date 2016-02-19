package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class NormalBuildersClubFrontpage extends Page {

	
	public NormalBuildersClubFrontpage(CatalogPage p) {
		super(p);
	}

	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("NORMAL");
        message.writeString("builders_club_frontpage");
        message.writeInt(0);
        message.writeInt(1);
        message.writeString(getPage().getHeadline());
        message.writeInt(3);
        message.writeInt(8554);
        message.writeString("builders_club_1_month");
        message.writeString("");
        message.writeInt(2560000);
        message.writeInt(2560000);
        message.writeInt(1024);
        message.writeInt(0);
        message.writeInt(0);
        message.writeBoolean(false);
        message.writeInt(8606);
        message.writeString("builders_club_14_days");
        message.writeString("");
        message.writeInt(2560000);
        message.writeInt(2560000);
        message.writeInt(1024);
        message.writeInt(0);
        message.writeInt(0);
        message.writeBoolean(false);
        message.writeInt(8710);
        message.writeString("builders_club_31_days");
        message.writeString("");
        message.writeInt(2560000);
        message.writeInt(2560000);
        message.writeInt(1024);
        message.writeInt(0);
        message.writeInt(0);
        message.writeBoolean(false);
	}
}
