package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.habbohotel.users.Preferences;

public class UserFactory {
	public UserFactory(){
		
	}
	public Habbo createHabbo(String sso) throws Exception{
		Habbo result = null;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM users WHERE sso=?");
		ps.setString(1, sso);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			result = new Habbo(
					rs.getInt("id"),
					rs.getString("username"),
					rs.getString("mail"),
					rs.getInt("rank"),
					rs.getString("look"),
					rs.getString("gender"),
					rs.getString("motto"),
					rs.getInt("credits"),
					rs.getInt("duckets"),
					rs.getInt("diamonds"),
					Long.parseLong(rs.getString("account_created")),
					readPreferences(rs.getInt("id")),
					Varoke.getFactory().getNavigatorFactory().readUserSearches(rs.getInt("id")),
					readFavorites(rs.getInt("id")),
					rs.getInt("home_room"),
					Varoke.getFactory().getMessengerFactory().buildMessenger(rs.getInt("id")),
					Varoke.getFactory().getRelationshipFactory().readRelationships(rs.getInt("id")),
					Varoke.getFactory().getBadgeFactory().buildBadgeComponent(rs.getInt("id")),
					Varoke.getFactory().getWardrobeFactory().buildWardrobe(rs.getInt("id")),
					Varoke.getFactory().getInventoryFactory().readInventory(rs.getInt("id"))
					);
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public Habbo getHabbo(int userId) throws Exception{
		if(Varoke.getSessionManager().getSessionByUserId(userId) != null)
			return Varoke.getSessionManager().getSessionByUserId(userId).getHabbo();
		return createHabbo((String)getValueFromUser("sso", userId));
	}
	public void saveHabbo(Habbo habbo){
		//TODO SAVE HABBO
	}
	public void updateValue(int userId, String key, Object Value) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE users SET " + key + "=? WHERE id=?");
		ps.setObject(1, Value);
		ps.setInt(2, userId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public Object getValueFromUser(String key, int Id)throws Exception{
		Object result = null;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT " + key + " FROM users WHERE id=?");
		ps.setInt(1, Id);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			result = rs.getObject(key);
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public int getIdFromUser(String username) throws Exception{
		int id = 0;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT id FROM users WHERE username=?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
			id = rs.getInt("id");
		Varoke.getFactory().dispose(cn, ps, rs);
		return id;
	}
	public HashMap<Integer, List<String>> readPermissions()throws Exception{
		HashMap<Integer, List<String>> result = new HashMap<Integer, List<String>>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM permissions");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			if(!result.containsKey(rs.getInt("rank")))
				result.put(rs.getInt("rank"), new ArrayList<String>());
			result.get(rs.getInt("rank")).add(rs.getString("value"));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public Preferences readPreferences(int userId) throws Exception{
		Preferences result = null;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM user_preferences WHERE userId=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		if(rs.next())
		{
			result = new Preferences(
					rs.getInt("userId"),
					rs.getString("volume"),
					rs.getInt("navi_x"),
					rs.getInt("navi_y"),
					rs.getInt("navi_width"),
					rs.getInt("navi_height")
					);
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void savePreferences(Preferences pref) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM user_preferences WHERE userId=?");
		ps.setObject(1, pref.getUserId());
		ps.execute();
		ps.close();
		ps = cn.prepareStatement("INSERT INTO user_preferences (userId,volume,navi_x,navi_y,navi_width, navi_height) VALUES (?,?,?,?,?,?)");
		ps.setInt(1, pref.getUserId());
		ps.setString(2, pref.getVolume());
		ps.setInt(3, pref.getNavigatorX());
		ps.setInt(4, pref.getNavigatorY());
		ps.setInt(5, pref.getNavigatorWidth());
		ps.setInt(6, pref.getNavigatorHeight());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public List<Integer> readFavorites(int userId) throws Exception{
		List<Integer> result = new ArrayList<Integer>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT roomId FROM user_favorites WHERE userId=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getInt("roomId"));
		}
		Varoke.getFactory().dispose(cn, ps, null);
		return result;
	}
	public void addFavorite(int userId, int roomId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO user_favorites (userId, roomId) VALUES (?,?)");
		ps.setInt(1, userId);
		ps.setInt(2, roomId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void removeFavorite(int userId, int roomId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM user_favorites WHERE userId=? AND roomId=?");
		ps.setInt(1, userId);
		ps.setInt(2, roomId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void saveObject(int userId, String key, Object value) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE users SET " + key + "=? WHERE id=?");
		ps.setObject(1, value);
		ps.setInt(2, userId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public boolean userExists(String username) throws Exception {
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT id FROM users WHERE username=?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		boolean result = rs.next();
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
}
