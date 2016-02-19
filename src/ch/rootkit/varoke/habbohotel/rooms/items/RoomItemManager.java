package ch.rootkit.varoke.habbohotel.rooms.items;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.items.AddFloorItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.AddWallItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.PickUpFloorItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.PickUpWallItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.UpdateFurniStackMapMessageComposer;
import ch.rootkit.varoke.habbohotel.comparators.RoomItemHeightComparator;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.RoomTileState;

public class RoomItemManager {
	private HashMap<Integer, RoomItem> floorItems;
	private HashMap<Integer, RoomItem> wallItems;
	private Room room;
	public RoomItemManager(Room r, HashMap<Integer, RoomItem> floor, HashMap<Integer, RoomItem> wall){
		floorItems = floor;
		wallItems = wall;
		room = r;
	}
	public void addFloorItem(RoomItem item) throws Exception{
		floorItems.put(item.getId(), item);
		getRoom().sendComposer(new AddFloorItemMessageComposer(item));
		getRoom().getGameMap().setWalkable(item.getX(), item.getY(), canWalk(item.getX(), item.getY()));
		getRoom().sendComposer(new UpdateFurniStackMapMessageComposer(item.getAffectedTiles(), getRoom()));
	}
	public void removeFloorItem(int itemId, int picker) throws Exception{
		RoomItem item = this.floorItems.get(itemId);
		getRoom().sendComposer(new PickUpFloorItemMessageComposer(item, picker));
		this.floorItems.remove(itemId);
		getRoom().sendComposer(new UpdateFurniStackMapMessageComposer(item.getAffectedTiles(), getRoom()));
		getRoom().getGameMap().setWalkable(item.getX(), item.getY(), canWalk(item.getX(), item.getY()));
	}
	public void addWallItem(RoomItem item) throws Exception{
		wallItems.put(item.getId(), item);
		getRoom().sendComposer(new AddWallItemMessageComposer(item));
	}
	public void removeWallItem(int itemId, int picker) throws Exception{
		getRoom().sendComposer(new PickUpWallItemMessageComposer(this.wallItems.get(itemId), picker));
		this.wallItems.remove(itemId);
	}
	public Room getRoom(){
		return room;
	}
	public RoomItem getFloorItem(int id){ return floorItems.get(id); }
	public List<RoomItem> getFloorItems(){ return new ArrayList<RoomItem>(floorItems.values()); }
	public List<RoomItem> getWallItems() { return new ArrayList<RoomItem>(wallItems.values()); }
	public RoomItem getItem(int id) {
		if(wallItems.containsKey(id))
			return wallItems.get(id);
		if(floorItems.containsKey(id))
			return floorItems.get(id);
		return null;
	}
	public void removeItem(int id, int picker) throws Exception{
		Varoke.getFactory().getItemFactory().pickItem(id);
		if(wallItems.containsKey(id))
			removeWallItem(id, picker);
		if(floorItems.containsKey(id))
			removeFloorItem(id, picker);
	}
	public boolean canPlace(int x, int y){
		List<RoomItem> itemsByHeight = getItemsOnSquare(x,y);
		boolean result = false;
		Collections.sort(itemsByHeight, new RoomItemHeightComparator());
		if(itemsByHeight.size() == 0)
			result = getRoom().getModel().getSquareStates()[x][y] == RoomTileState.OPEN;
		else
			result = itemsByHeight.get(0).getBaseItem().canStack();
		itemsByHeight.clear();
		itemsByHeight = null;
		return result;
	}

	public double getHeight(int x, int y) {
		List<RoomItem> itemsByHeight = getItemsOnSquare(x,y);
		double result = 0.0;
		Collections.sort(itemsByHeight, new RoomItemHeightComparator());
		if(itemsByHeight.size() == 0)
			result = getRoom().getModel().getHeight(x, y);
		else
			result = itemsByHeight.get(0).getFullHeight();
		itemsByHeight.clear();
		itemsByHeight = null;
		return result;
	}
	public List<RoomItem> getItemsOnSquare(int x, int y){
		List<RoomItem> result = new ArrayList<RoomItem>();
		for(RoomItem item : this.floorItems.values())
			for(Point tile : item.getAffectedTiles())
				if(tile.getX() == x && tile.getY() == y)
					result.add(item);
		return result;
	}
	public boolean canWalk(int x, int y) {
		List<RoomItem> itemsByHeight = getItemsOnSquare(x,y);
		boolean result = false;
		Collections.sort(itemsByHeight, new RoomItemHeightComparator());
		if(itemsByHeight.size() == 0)
			result = getRoom().getModel().getSquareStates()[x][y] == RoomTileState.OPEN;
		else
			result = itemsByHeight.get(0).getBaseItem().canWalk();
		itemsByHeight.clear();
		itemsByHeight = null;
		return result;
	}
}
