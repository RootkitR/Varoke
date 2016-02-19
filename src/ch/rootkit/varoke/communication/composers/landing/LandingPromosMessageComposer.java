package ch.rootkit.varoke.communication.composers.landing;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.landingview.Promotion;

public class LandingPromosMessageComposer implements MessageComposer {

	@Override
	public ServerMessage compose() throws Exception {
		ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(Varoke.getGame().getLanding().size());
		for(Promotion promo : Varoke.getGame().getLanding().getLandingViewItems()){
			promo.compose(result);
		}
		return result;
	}

}
