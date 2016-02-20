package ch.rootkit.varoke.communication.composers.badges;

import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.badges.Badge;
import ch.rootkit.varoke.habbohotel.users.badges.BadgeComponent;

public class UserBadgesMessageComposer implements MessageComposer {

	final BadgeComponent badgeComponent;
	
	public UserBadgesMessageComposer(BadgeComponent b){
		badgeComponent = b;
	}
	
	@Override
	public ServerMessage compose() throws Exception {
		final ServerMessage result = new ServerMessage(Outgoing.get(getClass().getSimpleName()));
		result.writeInt(badgeComponent.getUser());
		final List<Badge> badges = new ArrayList<Badge>();
		for(Badge b : badgeComponent.getBadges().values()){
			if(b.getSlot() > 0)
				badges.add(b);
		}
		result.writeInt(badges.size());
		for(Badge b : badges){
			result.writeInt(b.getSlot());
			result.writeString(b.getCode());
		}
		badges.clear();
		return result;
	}

}
