package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;
import ch.rootkit.varoke.habbohotel.rooms.RoomModel;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItemManager;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class RoomFactory {
	private HashMap<Integer, RoomData> roomDataCache;
	public RoomFactory(){
		roomDataCache = new HashMap<Integer, RoomData>();
	}
	public RoomData getRoomData(int roomId) throws Exception{
		if(roomDataCache.containsKey(roomId))
			return roomDataCache.get(roomId);
		return generateRoomData(roomId);
	}
	public List<RoomData> readUserRooms(Habbo habbo) throws Exception{
		return readUserRooms(habbo.getId());
	}
	public List<RoomData> readUserRooms(int Id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT id FROM rooms WHERE ownerId=?");
		ps.setInt(1, Id);
		ResultSet rs = ps.executeQuery();
		List<RoomData> result = new ArrayList<RoomData>();
		while(rs.next()){
			result.add(getRoomData(rs.getInt("id")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public List<RoomData> search(String query) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT id FROM rooms WHERE name LIKE ?");
		ps.setString(1, "%" + query + "%");
		ResultSet rs = ps.executeQuery();
		List<RoomData> result = new ArrayList<RoomData>();
		while(rs.next()){
			result.add(getRoomData(rs.getInt("id")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	private RoomData generateRoomData(int roomId) throws Exception{
		RoomData result = null;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM rooms WHERE id=?");
		ps.setInt(1, roomId);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result = new RoomData(
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("description"),
					rs.getString("model"),
					rs.getString("image"),
					rs.getInt("ownerId"),
					rs.getInt("category"),
					rs.getInt("max_user"),
					rs.getInt("state"),
					rs.getString("wallpaper"),
					rs.getString("floor"),
					rs.getString("landscape"),
					Varoke.stringToBool(rs.getString("allow_pets")),
					Varoke.stringToBool(rs.getString("allow_pets_eat")),
					Varoke.stringToBool(rs.getString("muted")),
					getTags(rs.getInt("id")),
					getRatings(rs.getInt("id")),
					rs.getInt("wall_height"),
					Varoke.stringToBool(rs.getString("allow_walkthrough")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		if(result != null)
			roomDataCache.put(roomId, result);
		return result;
	}
	public HashMap<String, RoomModel> getRoomModels() throws Exception{
		HashMap<String,RoomModel> result = new HashMap<String, RoomModel>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM room_models");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.put(rs.getString("id"), new RoomModel(
					rs.getString("id"),
					rs.getString("heightmap"),
					rs.getString("public_items"),
					rs.getInt("door_x"),
					rs.getInt("door_y"),
					rs.getInt("door_z"),
					rs.getInt("door_dir"),
					Varoke.stringToBool(rs.getString("club_only")),
					rs.getInt("divisor")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public List<Integer> getUserWithRights(int id) throws Exception {
		List<Integer> result = new ArrayList<Integer>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT userId FROM room_rights WHERE roomId=?");
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getInt("userId"));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void takeRights(int roomId, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM room_rights WHERE roomId=? AND userId=?");
		ps.setInt(1, roomId);
		ps.setInt(2,userId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void giveRights(int roomId, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO room_rights VALUES (?,?)");
		ps.setInt(1, roomId);
		ps.setInt(2,userId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public int createRoom(String name, String desc, String model, int category, int usersMax, int tradeState, Habbo session) throws Exception{
		int roomId = 0;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO rooms (name, description, model, ownerId, category, score, max_user, state, wallpaper, floor, landscape, allow_pets, allow_pets_eat, muted, trade_state) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		ps.setString(1, name);
		ps.setString(2,  desc);
		ps.setString(3, model);
		ps.setInt(4, session.getId());
		ps.setInt(5, category);
		ps.setInt(6, 0);
		ps.setInt(7, usersMax);
		ps.setInt(8, 0);
		ps.setString(9, "0.0");
		ps.setString(10, "0.0");
		ps.setString(11, "0.0");
		ps.setString(12, "1");
		ps.setString(13, "0");
		ps.setString(14, "0");
		ps.setInt(15, tradeState);
		ps.execute();
		ps.close();
		ps = Varoke.getDatabase().getConnection().prepareStatement("SELECT LAST_INSERT_ID();");
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			roomId = rs.getInt(1);
		Varoke.getFactory().dispose(cn, ps, rs);
		return roomId;	
	}
	public List<RoomData> searchByTag(String tag) throws Exception{
		List<RoomData> result = new ArrayList<RoomData>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT roomId FROM room_tags WHERE tag LIKE ?");
		ps.setString(1, tag);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(getRoomData(rs.getInt("roomId")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public List<String> getTags(int roomId) throws Exception{
		List<String> result = new ArrayList<String>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT tag FROM room_tags WHERE roomId=?");
		ps.setInt(1, roomId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getString("tag"));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void addTag(int roomId, String tag) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM room_tags WHERE roomId=? AND tag=?");
		ps.setInt(1, roomId);
		ps.setString(2, tag);
		ps.close();
		ps = cn.prepareStatement("INSERT INTO room_tags (roomId, tag) VALUES (?,?)");
		ps.setInt(1, roomId);
		ps.setString(2, tag);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void removeTag(int roomId, String tag) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM room_tags WHERE roomId=? AND tag=?");
		ps.setInt(1, roomId);
		ps.setString(2, tag);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public List<Integer> getRatings(int roomId) throws Exception{
		List<Integer> result = new ArrayList<Integer>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT userId FROM room_ratings WHERE roomId=?");
		ps.setInt(1, roomId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getInt("userId"));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void addRating(int roomId, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO room_ratings (roomId, userId) VALUES (?,?)");
		ps.setInt(1, roomId);
		ps.setInt(1, userId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public RoomItemManager createRoomItemManager(Room room) throws Exception{
		HashMap<Integer, RoomItem> floorItems = new HashMap<Integer, RoomItem>();
		HashMap<Integer, RoomItem> wallItems = new HashMap<Integer, RoomItem>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM items WHERE roomId=?");
		ps.setInt(1, room.getId());
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			RoomItem item = new RoomItem(rs.getInt("id"),
					rs.getInt("ownerId"),
					rs.getInt("roomId"),
					rs.getInt("baseItem"),
					rs.getString("extraData"),
					rs.getInt("x"),
					rs.getInt("y"),
					rs.getDouble("z"),
					rs.getInt("rotation"),
					rs.getString("wallPosition"),
					rs.getInt("limitedId"),
					rs.getInt("groupId"),
					Varoke.stringToBool(rs.getString("isBuildersFurni")));
			if(item.getBaseItem().getType().toLowerCase().equals("s"))
				floorItems.put(item.getId(), item);
			else
				wallItems.put(item.getId(), item);
		}
		return new RoomItemManager(room, floorItems, wallItems);
	}
}
