package ch.rootkit.varoke.habbohotel.users.inventory;

import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.inventory.NewInventoryObjectMessageComposer;
import ch.rootkit.varoke.communication.composers.inventory.RemoveInventoryObjectMessageComposer;
import ch.rootkit.varoke.communication.composers.inventory.UpdateInventoryMessageComposer;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class Inventory {

	private HashMap<Integer, InventoryItem> items;
	private Habbo habbo;
	
	public Inventory(Habbo user, HashMap<Integer, InventoryItem> itms) {
		items = itms;
		habbo = user;
	}
	
	public HashMap<Integer, InventoryItem> getItems(){ 
		return items;
	}
	
	public Habbo getHabbo(){ 
		return habbo;
	}
	
	public InventoryItem createItem(int userId, int baseItem, String extraData, int limitedId, int groupId, boolean isBuildersFurni) throws Exception{
		InventoryItem item = Varoke.getFactory().getInventoryFactory().createItem(userId, baseItem, extraData, limitedId, groupId, isBuildersFurni);
		this.items.put(item.getId(), item);
		getHabbo().getSession().sendComposer(new UpdateInventoryMessageComposer());
		getHabbo().getSession().sendComposer(new NewInventoryObjectMessageComposer(1, item.getId()));
		return item;
	}
	
	public void removeItem(int id) throws Exception{
		this.items.remove(id);
		getHabbo().getSession().sendComposer(new RemoveInventoryObjectMessageComposer(id));
	}
	
	public InventoryItem getItem(int id){
		return this.items.get(id);
	}
	
	public void addItem(int id, int baseItemId, String extraData, int limitedId, int groupId, boolean buildersFurni) {
		InventoryItem item = new InventoryItem(id,this.getHabbo().getId(), baseItemId, groupId, limitedId, extraData, buildersFurni);
		this.items.put(item.getId(), item);
		getHabbo().getSession().sendComposer(new UpdateInventoryMessageComposer());
		getHabbo().getSession().sendComposer(new NewInventoryObjectMessageComposer(1, item.getId()));
	}
}
