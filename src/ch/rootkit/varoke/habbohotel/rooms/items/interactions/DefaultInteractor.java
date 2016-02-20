package ch.rootkit.varoke.habbohotel.rooms.items.interactions;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class DefaultInteractor extends Interactor {

	public DefaultInteractor(RoomItem item) {
		super(item);
	}
	
	@Override
	public void onPlace(RoomUser user) throws Exception{
		super.onPlace(user);
	}
	
	@Override
	public void onTrigger(RoomUser user, boolean withRights) throws Exception{
		super.onTrigger(user, withRights);
		int currentInteraction = Integer.parseInt(StringUtils.isNullOrEmpty(getItem().getExtraData()) ? "0" : getItem().getExtraData());
		if(currentInteraction < (getItem().getBaseItem().getInteractionsModes() - 1)){
			getItem().setExtraData((currentInteraction  + 1) + "");
			getItem().update();
		}else if(currentInteraction == (getItem().getBaseItem().getInteractionsModes() - 1)){
			getItem().setExtraData("0");
			getItem().update();
		}
	}
	
	@Override
	public void onWalk(RoomUser user) throws Exception {
		super.onWalk(user);
		System.out.println("Walks on");
	}
	
	@Override
	public void onWalkOff(RoomUser user) throws Exception {
		super.onWalkOff(user);
		System.out.println("Walks off");
	}

}
