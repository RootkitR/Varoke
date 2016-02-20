package ch.rootkit.varoke.habbohotel.users.wardrobe;

import java.util.HashMap;

public class Wardrobe {
	
	HashMap<Integer, WardrobeItem> items;
	
	public Wardrobe(HashMap<Integer, WardrobeItem> i){
		items = i;
	}
	
	public void addItem(WardrobeItem item){
		if(!items.containsKey(item.getSlot()))
			items.put(item.getSlot(), item);
	}
	
	public void removeItem(int slot){
		if(items.containsKey(slot))
			items.remove(slot);
	}
	
	public HashMap<Integer, WardrobeItem> getItems(){
		return items;
	}
	
	public int size(){
		return items.size();
	}
}
