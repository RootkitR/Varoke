package ch.rootkit.varoke.communication.events.rooms.items;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.items.AddFloorItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.OpenGiftMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.PickUpFloorItemMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.items.Item;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class OpenGiftMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		int itemId = event.readInt();
		if(session.getHabbo().getCurrentRoom() == null || !session.getHabbo().getCurrentRoom().hasRights(session.getHabbo(), true) || session.getHabbo().getCurrentRoom().getItemManager().getItem(itemId) == null)
			return;
		RoomItem item = session.getHabbo().getCurrentRoom().getItemManager().getItem(itemId);
		Item baseItem = Varoke.getGame().getItemManager().getItem(Varoke.getFactory().getInventoryFactory().readGift(item.getId()));
		String extraData = Varoke.getFactory().getInventoryFactory().readGiftExtraData(item.getId());
		if(baseItem == null)
			return;
		item.setExtraData(item.getExtraData() + ";" + "true");
		item.update();
		boolean isNewPresent = item.getBaseItem().getItemName().startsWith("present_wrap");
		if(isNewPresent)
			item.setGiftTimer(5); // 2.5 Seconds
		session.sendComposer(new OpenGiftMessageComposer(baseItem, extraData));
		item.setBaseItem(baseItem.getId());
		item.setExtraData(extraData);
		Varoke.getFactory().getItemFactory().saveItemState(item);
		if(!isNewPresent){
			session.getHabbo().getCurrentRoom().sendComposer(new PickUpFloorItemMessageComposer(item, 0));
			session.getHabbo().getCurrentRoom().sendComposer(new AddFloorItemMessageComposer(item));
		}
		Varoke.getFactory().getInventoryFactory().removeGift(item.getId());
	}

}
