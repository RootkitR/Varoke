package ch.rootkit.varoke.communication.composers.navigator;

import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.navigator.NavigatorSearch;

public class NavigatorSavedSearchesComposer implements MessageComposer {

	final List<NavigatorSearch> naviSearch;
	public NavigatorSavedSearchesComposer(List<NavigatorSearch> searches){
		naviSearch = searches;
	}
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(naviSearch.size());
		for(NavigatorSearch n : naviSearch){
			result.writeInt(n.getId());
			result.writeString(n.getValue1().length() > 0 ? n.getValue1() : "query");
			result.writeString(n.getSearchText());
			result.writeString("");
		}
		return result;
	}

}
