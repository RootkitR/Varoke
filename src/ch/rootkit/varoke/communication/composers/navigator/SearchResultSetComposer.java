package ch.rootkit.varoke.communication.composers.navigator;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.Varoke;

public class SearchResultSetComposer implements MessageComposer {

	final Session Session;
	final String Value1;
	final String Value2;
	public SearchResultSetComposer(Session session, String val1, String val2){
		Session = session;
		Value1 = val1;
		Value2 = val2;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeString(Value1);
		result.writeString(Value2);
		result.writeInt(Value2.length() > 0 ? 1 : Varoke.getGame().getNavigator().getNavigatorLength(Value1, Session.getHabbo()));
		if(Value2.length() > 0)
			Varoke.getGame().getNavigator().performSearch(Value1, Value2, Session, result);
		else if(Varoke.getGame().getNavigator().isCateogry(Value1))
			Varoke.getGame().getNavigator().getCategory(Value1).serialize(result);
		else if(Varoke.getGame().getNavigator().isFlatcat(Value1))
			Varoke.getGame().getNavigator().getFlatCat(Value1).serialize(result, true);
		else
			Varoke.getGame().getNavigator().serializeNavigator(Value1, true, Session, result);
		return result;
	}

}
