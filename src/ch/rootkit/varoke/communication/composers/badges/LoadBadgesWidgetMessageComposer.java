package ch.rootkit.varoke.communication.composers.badges;

import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.badges.Badge;
import ch.rootkit.varoke.habbohotel.users.badges.BadgeComponent;

public class LoadBadgesWidgetMessageComposer implements MessageComposer {

	final BadgeComponent badgeComponent;
	
	public LoadBadgesWidgetMessageComposer(BadgeComponent comp){
		badgeComponent = comp;
	}
	
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(badgeComponent.size());
		final List<Badge> wearing = new ArrayList<Badge>();
		for(Badge b : badgeComponent.getBadges().values()){
			result.writeInt(1);
			result.writeString(b.getCode());
			if(b.getSlot() > 0)
				wearing.add(b);
		}
		result.writeInt(wearing.size());
		for(Badge b : wearing){
			result.writeInt(b.getSlot());
			result.writeString(b.getCode());
		}
		wearing.clear();
		return result;
	}

}
