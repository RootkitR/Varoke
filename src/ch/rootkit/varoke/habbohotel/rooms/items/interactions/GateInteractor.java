package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class GateInteractor extends Interactor{

	public GateInteractor(RoomItem item) {
		super(item);
	}
	
	@Override
	public void onPlace(RoomUser user) throws Exception {
		super.onPlace(user);
	}

	@Override
	public void onTrigger(RoomUser user, boolean withRights) throws Exception {
		super.onTrigger(user, withRights);
		if(!withRights)
			return;
		getItem().setExtraData(
				(getItem().getExtraData().equals("0") || 
				StringUtils.isNullOrEmpty(getItem().getExtraData())) ? 
						"1" : "0");
		getItem().update();
		
	}

	@Override
	public void onWalk(RoomUser user) throws Exception {
		super.onWalk(user);
	}

	@Override
	public void onWalkOff(RoomUser user) throws Exception {
		super.onWalkOff(user);
	}
	
	@Override
	public boolean isWalkable() {
		return getItem().getExtraData().equals("1");
	}
}
