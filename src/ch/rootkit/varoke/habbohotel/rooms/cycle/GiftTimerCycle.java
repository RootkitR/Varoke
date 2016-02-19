package ch.rootkit.varoke.habbohotel.rooms.cycle;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.items.AddFloorItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.PickUpFloorItemMessageComposer;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.utils.Logger;

public class GiftTimerCycle extends ItemCycle{

	public GiftTimerCycle(Room r) {
		super(r);
	}

	@Override
	public void onCycle(RoomItem roomItem) {
		try{
			if(roomItem == null)
				return;
			if(roomItem.getGiftTimer() > 1){
				roomItem.setGiftTimer(roomItem.getGiftTimer() - 1);
			}else if(roomItem.getGiftTimer() == 1){
				if(roomItem.isFloorItem()){
					getRoom().sendComposer(new PickUpFloorItemMessageComposer(roomItem, 0));
					getRoom().sendComposer(new AddFloorItemMessageComposer(roomItem));
				}else{
					Session session = Varoke.getSessionManager().getSessionByUserId(roomItem.getOwnerId());
					if(session == null)
						return;
					session.getHabbo().getCurrentRoom().getItemManager().removeItem(roomItem.getId(), session.getHabbo().getId());
					session.getHabbo().getInventory().addItem(roomItem.getId(), roomItem.getBaseItemId(), roomItem.getExtraData(), roomItem.getLimitedId(), roomItem.getGroupId(), roomItem.isBuildersFurni());
				}
				roomItem.setGiftTimer(0);
			}
		}catch(Exception ex){
			Logger.printErrorLine("Error in Room Cycle Task: Update gift Timer");
		}
	}

}
