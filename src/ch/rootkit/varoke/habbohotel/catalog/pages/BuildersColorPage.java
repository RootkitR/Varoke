package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class BuildersColorPage extends Page {

	public BuildersColorPage(CatalogPage p) {
		super(p);
	}
	@Override
	public void compose(ServerMessage message) throws Exception {
		message.writeString("BUILDERS_CLUB");
        message.writeString("default_3x3_color_grouping");
        message.writeInt(3);
        message.writeString(getPage().getHeadline());
        message.writeString(getPage().getTeaser());
        message.writeString(getPage().getPageSpecial());
        message.writeInt(3);
        message.writeString(getPage().getText1());
        message.writeString(getPage().getTextDetails().replace("[10]", (char)10 + "")
            .replace("[13]", (char)13 + ""));
        message.writeString(getPage().getTextTeaser().replace("[10]", (char)10 + "")
            .replace("[13]", (char)13 + ""));
	}
}
