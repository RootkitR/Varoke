package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;

public class InventoryFactory {

	public HashMap<Integer, InventoryItem> readInventory(int userId) throws Exception {
		HashMap<Integer, InventoryItem> result = new HashMap<Integer, InventoryItem>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM items WHERE ownerId=? AND roomId=0");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.put(rs.getInt("id"), new InventoryItem(
					rs.getInt("id"),
					userId,
					rs.getInt("baseItem"),
					rs.getInt("groupId"),
					rs.getInt("limitedId"),
					rs.getString("extraData"),
					Varoke.stringToBool(rs.getString("isBuildersFurni"))
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public InventoryItem createItem(int userId, int baseItem, String extraData, int limitedId, int groupId, boolean isBuildersFurni) throws Exception{
		InventoryItem result = null;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO items (ownerId,baseItem,extraData,limitedId,groupId,isBuildersFurni) VALUES (?,?,?,?,?,?)");
		ps.setInt(1, userId);
		ps.setInt(2, baseItem);
		ps.setString(3,extraData);
		ps.setInt(4, limitedId);
		ps.setInt(5, groupId);
		ps.setString(6, isBuildersFurni ? "1" : "0");
		ps.execute();
		ResultSet rs = ps.executeQuery("SELECT LAST_INSERT_ID();");
		rs.next();
		int id = rs.getInt(1);
		result = new InventoryItem(id, userId, baseItem, groupId, limitedId, extraData, isBuildersFurni);
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void storeGift(int itemId, int baseItem, String extraData) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO user_gifts (itemId,baseItem,extraData) VALUES (?,?,?);");
		ps.setInt(1, itemId);
		ps.setInt(2, baseItem);
		ps.setString(3, extraData);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void updateExtraData(InventoryItem inventoryItem) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE items SET extraData=? WHERE id=?");
		ps.setString(1, inventoryItem.getExtraData());
		ps.setInt(2, inventoryItem.getId());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public int readGift(int giftId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT baseItem FROM user_gifts WHERE itemId=?");
		ps.setInt(1, giftId);
		ResultSet rs = ps.executeQuery();
		int result = 0;
		if(rs.next()){
			result = rs.getInt("baseItem");
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public String readGiftExtraData(int id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT extraData FROM user_gifts WHERE itemId=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		String result = "";
		if(rs.next()){
			result = rs.getString("extraData");
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void removeGift(int id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM user_gifts WHERE itemId=?");
		ps.setInt(1, id);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
}
