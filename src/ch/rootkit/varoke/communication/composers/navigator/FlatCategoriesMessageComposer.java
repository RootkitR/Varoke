package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.navigator.FlatCat;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class FlatCategoriesMessageComposer implements MessageComposer {

	final Session Session;
	public FlatCategoriesMessageComposer(Session session){
		Session = session;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Varoke.getGame().getNavigator().getFlats().size());
		for(FlatCat fc : Varoke.getGame().getNavigator().getFlats()){
			result.writeInt(fc.getId());
			result.writeString(fc.getCaption());
			result.writeBoolean(Session.getHabbo().getRank() >= fc.getMinRank());
			result.writeBoolean(false);
			result.writeString("NONE");
			result.writeString("");
			result.writeBoolean(false);
		}
		return result;
	}

}
