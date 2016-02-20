package ch.rootkit.varoke.habbohotel.items;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.utils.Logger;

public class ItemManager {

	HashMap<Integer, Item> items;
	
	public ItemManager(){
		items = new HashMap<Integer, Item>();
	}
	
	public void initialize()throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Initializing ItemManager ");
		items = Varoke.getFactory().getItemFactory().readItems();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	
	public Item getItem(int id){
		return this.items.get(id);
	}
	
	public Item getItemByName(String name){
		for(Item i : this.items.values()){
			if(i.getItemName().equals(name))
			return i;
		}
		return null;
	}
	
	public List<Item> getItems(){
		return new ArrayList<Item>(items.values());
	}
	
	public void dispose(){
		items.clear();
	}
	
	public Item getItemBySprite(int sprite) {
		for(Item i : this.items.values()){
			if(i.getSpriteId() == sprite)
				return i;
		}
		return null;
	}
}
