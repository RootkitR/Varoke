package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;

public class RoomAddFloorItemMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		if(!session.getHabbo().getCurrentRoom().hasRights(session.getHabbo(), false)){
			System.out.println("No rights");
			return;}
		String plainData = event.readString();
		String[] placementData = plainData.split(" ");
		int itemId = Integer.parseInt(placementData[0].replace("-", ""));
		InventoryItem item = session.getHabbo().getInventory().getItem(itemId);
		if(item == null){return;}
		String type = placementData[1].startsWith(":") ? "wall" : "floor";
		switch(type){
		case "floor":{
			int x = Integer.parseInt(placementData[1]);
			int y = Integer.parseInt(placementData[2]);
			int rot = Integer.parseInt(placementData[3]);
			if(!session.getHabbo().getCurrentRoom().getItemManager().canPlace(x, y))
				return;
			double height = session.getHabbo().getCurrentRoom().getItemManager().getHeight(x, y);
			RoomItem roomItem = Varoke.getFactory().getItemFactory().addFloorItem(session.getHabbo().getCurrentRoomId(), x,y, rot, height, item);
			session.getHabbo().getCurrentRoom().getItemManager().addFloorItem(roomItem);
			session.getHabbo().getInventory().removeItem(item.getId());
			break;
		}
		case "wall":{
			RoomItem roomItem = Varoke.getFactory().getItemFactory().addWallItem(session.getHabbo().getCurrentRoomId(), ":" + plainData.split(":")[1], item);
			session.getHabbo().getCurrentRoom().getItemManager().addWallItem(roomItem);
			session.getHabbo().getInventory().removeItem(item.getId());
			break;
		}
		}
	}

}
