package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.messenger.Friend;
import ch.rootkit.varoke.habbohotel.messenger.Messenger;
import ch.rootkit.varoke.habbohotel.messenger.Request;
import ch.rootkit.varoke.habbohotel.messenger.Result;
import ch.rootkit.varoke.habbohotel.messenger.offline.OfflineMessage;

public class MessengerFactory {

	public MessengerFactory(){
		
	}
	public HashMap<Integer, Friend> loadFriends(int userId) throws Exception{
		HashMap<Integer, Friend> result = new HashMap<Integer, Friend>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT friend_two FROM messenger_friends WHERE friend_one=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			int uId = rs.getInt("friend_two");
			result.put(uId, buildFriend(uId, userId));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public HashMap<Integer, Request> loadRequests(int userId) throws Exception{
		HashMap<Integer, Request> result = new HashMap<Integer, Request>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT `from` FROM messenger_requests WHERE `to`=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			int uId = rs.getInt("from");
			result.put(uId, buildRequest(uId));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public Messenger buildMessenger(int userId) throws Exception{
		return new Messenger(userId, loadFriends(userId), loadRequests(userId));
	}
	public Friend buildFriend(int userId, int from) throws Exception{
		return new Friend(userId,
				(String)Varoke.getFactory().getUserFactory().getValueFromUser("username", userId),
				(String)Varoke.getFactory().getUserFactory().getValueFromUser("look", userId),
				(String)Varoke.getFactory().getUserFactory().getValueFromUser("motto", userId),
				Varoke.stringToBool((String)Varoke.getFactory().getUserFactory().getValueFromUser("appear_offline", userId)),
				Varoke.stringToBool((String)Varoke.getFactory().getUserFactory().getValueFromUser("hide_room", userId)),
				from
				);
	}
	public Request buildRequest(int userId) throws Exception{
		return new Request(userId,
				(String)Varoke.getFactory().getUserFactory().getValueFromUser("username", userId),
				(String)Varoke.getFactory().getUserFactory().getValueFromUser("look", userId)
				);
	}
	public void removeFriend(int user1, int user2) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM messenger_friends WHERE friend_one=? AND friend_two=?");
		ps.setInt(1, user1);
		ps.setInt(2, user2);
		ps.execute();
		ps.clearParameters();
		ps.setInt(1, user2);
		ps.setInt(2, user1);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void removeRequest(int from, int to) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM messenger_requests WHERE `from`=? AND `to`=?");
		ps.setInt(1, from);
		ps.setInt(2, to);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void addFriend(int from, int to)throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO messenger_friends (friend_one, friend_two) VALUES (?,?)");
		ps.setInt(1, from);
		ps.setInt(2, to);
		ps.execute();
		ps.clearParameters();
		ps.setInt(1, to);
		ps.setInt(2, from);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void removeAllRequests(int user) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM messenger_requests WHERE `to`=?");
		ps.setInt(1, user);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void createRequest(int fromUser, int toUser) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO messenger_requests (`from`,`to`) VALUES (?,?)");
		ps.setInt(1, fromUser);
		ps.setInt(2, toUser);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public boolean requestExists(int from, int to) throws Exception{
		boolean result = false;
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT null FROM messenger_requests WHERE `from`=? AND `to`=?");
		ps.setInt(1, from);
		ps.setInt(2, to);
		result = ps.executeQuery().next();
		ps.clearParameters();
		ps.setInt(1, to);
		ps.setInt(2, from);
		result = result || ps.executeQuery().next();
		Varoke.getFactory().dispose(cn, ps, null);
		return result;
	}
	public void store(int fromUser,int toUser,int roomId, String Message, String Type) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO chatlog (`from`,`to`,`roomId`,`message`,`type`,`timestamp`) VALUES (?,?,?,?,?,?)");
		ps.setInt(1, fromUser);
		ps.setInt(2, toUser);
		ps.setInt(3, roomId);
		ps.setString(4, Message);
		ps.setString(5, Type);
		ps.setString(6, Varoke.getCurrentTimestamp() +"");
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void storeOffline(OfflineMessage message) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO offline_messages (`from`,`to`,`message`,`time`) VALUES (?,?,?,?)");
		ps.setInt(1,message.getFromId());
		ps.setInt(2, message.getToId());
		ps.setString(3, message.getMessage());
		ps.setLong(4, message.getTime());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void removeOfflineMessage(OfflineMessage message) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM offline_messages WHERE `from`=? AND `to`=? AND `message`=? AND `time`=?");
		ps.setInt(1, message.getFromId());
		ps.setInt(2, message.getToId());
		ps.setString(3, message.getMessage());
		ps.setLong(4, message.getTime());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public List<OfflineMessage> readOfflineMessages() throws Exception{
		List<OfflineMessage> result = new ArrayList<OfflineMessage>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM offline_messages");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new OfflineMessage(
					rs.getInt("from"),
					rs.getInt("to"),
					rs.getString("message"),
					rs.getLong("time")
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public List<Result> search(String query) throws Exception{
		List<Result> result = new ArrayList<Result>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT id,username, motto, look, last_login FROM users WHERE username LIKE ?");
		ps.setString(1, query + "%");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new Result(
					rs.getInt("id"),
					rs.getString("username"), 
					rs.getString("motto"),
					rs.getString("look"),
					Long.parseLong(rs.getString("last_login"))
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
}
