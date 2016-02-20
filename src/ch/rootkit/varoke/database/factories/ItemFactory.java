package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.items.Item;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;

public class ItemFactory {
	
	public ItemFactory(){
	}
	
	public HashMap<Integer, Item> readItems() throws Exception{
		HashMap<Integer, Item> result = new HashMap<Integer, Item>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * from furnitures");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.put(rs.getInt("id"), new Item(
					rs.getInt("id"),
					rs.getInt("sprite_id"),
					rs.getInt("flat_id"),
					rs.getString("item_name"),
					rs.getString("public_name"),
					rs.getString("type"),
					rs.getString("stack_height"),
					Varoke.stringToBool(rs.getString("can_stack")),
					Varoke.stringToBool(rs.getString("allow_recycle")),
					Varoke.stringToBool(rs.getString("allow_trade")),
					Varoke.stringToBool(rs.getString("allow_marketplace_sell")),
					Varoke.stringToBool(rs.getString("allow_gift")),
					Varoke.stringToBool(rs.getString("allow_inventory_stack")),
					rs.getString("interaction_type"),
					rs.getInt("interaction_modes_count"),
					rs.getString("vending_ids"),
					Varoke.stringToBool(rs.getString("subscriber")),
					rs.getInt("effectid"),
					rs.getInt("x"),
					rs.getInt("y"),
					Varoke.stringToBool(rs.getString("can_sit")),
					Varoke.stringToBool(rs.getString("can_walk"))
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	
	public RoomItem addFloorItem(int roomId, int x, int y, int rot, double height, InventoryItem item) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE items SET roomId=?, x=?, y=?, z=?, rotation=?, limitedId=?, groupId=?, isBuildersFurni=? WHERE id=?");;
		ps.setInt(1, roomId);
		ps.setInt(2, x);
		ps.setInt(3, y);
		ps.setDouble(4, height);
		ps.setInt(5, rot);
		ps.setInt(6, item.getLimitedId());
		ps.setInt(7, item.getGroupId());
		ps.setString(8, item.isBuildersClubItem() ? "1" : "0");
		ps.setInt(9, item.getId());
		ps.execute();
		RoomItem result = new RoomItem(item.getId(), item.getUserId(), roomId, item.getBaseId(), item.getExtraData(), x, y, height, rot, "", item.getLimitedId(), item.getBaseId(), item.isBuildersClubItem());
		Varoke.getFactory().dispose(cn, ps, null);
		return result;
	}
	
	public RoomItem addWallItem(int roomId, String wallPosition, InventoryItem item) throws Exception {
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE items SET roomId=?, wallPosition=? WHERE id=?");
		ps.setInt(1, roomId);
		ps.setString(2, wallPosition);
		ps.setInt(3, item.getId());
		ps.execute();
		RoomItem result = new RoomItem(item.getId(), item.getUserId(), roomId, item.getBaseId(), item.getExtraData(), 0, 0, 0.0, 0, wallPosition, item.getLimitedId(), item.getBaseId(), item.isBuildersClubItem());
		Varoke.getFactory().dispose(cn, ps, null);
		return result;
	}
	
	public void saveItemState(RoomItem item) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE items SET x=?, y=?, z=?, rotation=?, extraData=?, baseItem=?, wallPosition=? WHERE id=?");
		ps.setInt(1, item.getX());
		ps.setInt(2, item.getY());
		ps.setDouble(3, item.getZ());
		ps.setInt(4, item.getRotation());
		ps.setString(5, item.getExtraData());
		ps.setInt(6, item.getBaseItemId());
		ps.setString(7, item.getWallPosition());
		ps.setInt(8, item.getId());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	public void pickItem(int id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE items SET roomId=0 WHERE id=?");
		ps.setInt(1, id);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	public void remove(int id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM items WHERE id=?");
		ps.setInt(1, id);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
}
