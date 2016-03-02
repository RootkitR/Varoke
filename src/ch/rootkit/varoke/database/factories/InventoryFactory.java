package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;

public class InventoryFactory {

	/**
	 * Reads an User Inventory from Database
	 * @param userId id of user
	 * @return Hashmap with item id and InventoryItem Instance
	 * @throws Exception
	 */
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
	
	/**
	 * Creates an InventoryItem (in database)
	 * @param userId id of user
	 * @param baseItem the base Item Id (from furnitures)
	 * @param extraData some Extra Parameters (like gift message, current state)
	 * @param limitedId the limited Id
	 * @param groupId the group Id
	 * @param isBuildersFurni if the furni is from builders club
	 * @return InventoryItem Instance
	 * @throws Exception
	 */
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
	
	/**
	 * Saves a gift into the database
	 * @param itemId the item id (from items)
	 * @param baseItem the base item id (from furnitures)
	 * @param extraData some Extra Parameters (like gift message, current state)
	 * @throws Exception
	 */
	public void storeGift(int itemId, int baseItem, String extraData) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO user_gifts (itemId,baseItem,extraData) VALUES (?,?,?);");
		ps.setInt(1, itemId);
		ps.setInt(2, baseItem);
		ps.setString(3, extraData);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * Saves the current ExtraData to Database
	 * @param inventoryItem the Item which should be saved
	 * @throws Exception
	 */
	public void updateExtraData(InventoryItem inventoryItem) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE items SET extraData=? WHERE id=?");
		ps.setString(1, inventoryItem.getExtraData());
		ps.setInt(2, inventoryItem.getId());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * Reads the baseItem Id of a gift from Database
	 * @param giftId the gift id
	 * @return the base Item Id
	 * @throws Exception
	 */
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
	
	/**
	 * Reads the ExtraData of a gift from Database
	 * @param id the gift id
	 * @return the ExtraData
	 * @throws Exception
	 */
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
	
	/**
	 * deletes the gift from database
	 * @param id the gift id
	 * @throws Exception
	 */
	public void removeGift(int id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM user_gifts WHERE itemId=?");
		ps.setInt(1, id);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * saves teleporters to database
	 * @param id the first teleporter
	 * @param id2 the second teleporter
	 * @throws Exception
	 */
	public void createTeleporter(int id, int id2) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO teleporter (`tele_one`,`tele_two`) VALUES (?,?);");
		ps.setInt(1, id);
		ps.setInt(2, id2);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * reads the other teleporter id from database
	 * @param tele item Id
	 * @return second teleporter Id
	 * @throws Exception
	 */
	public int getOtherTele(int tele) throws Exception{
		int result = 0;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM teleporter WHERE tele_one=? OR tele_two=?");
		ps.setInt(1, tele);
		ps.setInt(2, tele);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			int teleOne = rs.getInt("tele_one");
			int teleTwo = rs.getInt("tele_two");
			if(teleOne == tele){
				result = teleTwo;
			}else if(teleTwo == tele){
				result = teleOne;
			}
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}

	/**
	 * Reads the Room Id of an Item
	 * @param id the item Id
	 * @return the room id
	 * @throws Exception
	 */
	public int getRoomByItem(int id) throws Exception{
		int result = 0;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT roomId FROM items WHERE id=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result = rs.getInt("roomId");
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
}
