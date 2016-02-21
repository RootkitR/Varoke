package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.pathfinding.Rotation;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.threading.executables.VendingAction;

public class VendorInteractor extends Interactor {

	public VendorInteractor(RoomItem item) {
		super(item);
	}
	
	@Override
	public void onPlace(RoomUser user) throws Exception{
		super.onPlace(user);
	}
	
	@Override
	public void onTrigger(RoomUser user, boolean withRights) throws Exception{
		super.onTrigger(user, withRights);
		if(!getItem().getRoom().getGameMap().tilesTouching(
				new Point(getItem().getX(), getItem().getY())
				, user.getPosition())){
			user.setGoal(getItem().inFront());
			user.setPathFinderCollection();
			return;
		}
		user.setRotation(Rotation.calculate(user.getPosition().getX(), user.getPosition().getY(), getItem().getX(), getItem().getY()));
		user.updateStatus();
		getItem().setExtraData("1");
		getItem().update();
		Varoke.getGame().getThreadPool().executeScheduled(new VendingAction(getItem(), user), 500);
	}
	
	@Override
	public void onWalk(RoomUser user) throws Exception {
		super.onWalk(user);
	}
	
	@Override
	public void onWalkOff(RoomUser user) throws Exception {
		super.onWalkOff(user);
	}
}
