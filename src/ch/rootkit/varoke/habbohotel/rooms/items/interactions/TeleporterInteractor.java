package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class TeleporterInteractor extends Interactor {

	public TeleporterInteractor(RoomItem item) {
		super(item);
	}
	
	@Override
	public void onPlace(RoomUser user) throws Exception{
		super.onPlace(user);
	}
	
	@Override
	public void onTrigger(RoomUser user, boolean withRights) throws Exception{
		super.onTrigger(user, withRights);
		if (this.canTeleport(user)) {
            user.getClient().getHabbo().setTeleportItem(getItem());
            user.getClient().getHabbo().setTeleportStep(1);
            getItem().setExtraData("1");
            getItem().update();
        } else {
            user.setGoal(getItem().inFront());
            user.setPathFinderCollection(true);
        }
	}
	
	@Override
	public void onWalk(RoomUser user) throws Exception {
		super.onWalk(user);
	}
	
	@Override
	public void onWalkOff(RoomUser user) throws Exception {
		super.onWalkOff(user);
	}
	
	private boolean canTeleport(RoomUser user) throws Exception{
	     if (user.getClient().getHabbo().getTeleportItem() != null){
	    	 System.out.println("still using teleporter");
	            return false;
	     }
	     if (!(getItem().getExtraData().equals("0") || getItem().getExtraData().isEmpty())){
	    	 System.out.println("extradata not null");
	            return false;
	     }
		return true;
	}
}
