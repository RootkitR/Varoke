package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class PressurePlateInteractor extends Interactor {

	public PressurePlateInteractor(RoomItem item) {
		super(item);
	}

	@Override
	public void onPlace(RoomUser user) throws Exception {
		super.onPlace(user);
	}

	@Override
	public void onTrigger(RoomUser user, boolean withRights) throws Exception {
		super.onTrigger(user, withRights);
	}

	@Override
	public void onWalk(RoomUser user) throws Exception {
		super.onWalk(user);
		getItem().setExtraData("1");
		getItem().update();
	}

	@Override
	public void onWalkOff(RoomUser user) throws Exception {
		super.onWalkOff(user);
		getItem().setExtraData("0");
		getItem().update();
	}

}
