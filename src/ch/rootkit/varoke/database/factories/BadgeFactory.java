package ch.rootkit.varoke.database.factories;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.badges.Badge;
import ch.rootkit.varoke.habbohotel.users.badges.BadgeComponent;

public class BadgeFactory {
	
	public BadgeFactory(){
	}
	
	/**
	 * gives the user the badge
	 * @param badge the badge
	 * @param userId id of user
	 * @throws Exception
	 */
	public void giveBadge(String badge, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO user_badges (userId,badge,slot) VALUES (?,?,0)");
		ps.setInt(1, userId);
		ps.setString(2, badge);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * takes a badge from a user
	 * @param badge the badge
	 * @param userId id of user
	 * @throws Exception
	 */
	public void takeBadge(String badge, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM user_badges WHERE userId=? AND badge=?");
		ps.setInt(1, userId);
		ps.setString(2, badge);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * Creates a HashMap with all Badge from the user.
	 * @param userId id of user
	 * @return HashMap with BadgeCode and a new Instance of the badge
	 * @throws Exception
	 */
	public HashMap<String, Badge> readBadges(int userId) throws Exception{
		HashMap<String, Badge> result = new HashMap<String, Badge>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT badge, slot FROM user_badges WHERE userId=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			if(!result.containsKey(rs.getString("badge")))
				result.put(rs.getString("badge"), new Badge(rs.getString("badge"), rs.getInt("slot")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	
	/**
	 * builds the BadgeComponent for an user
	 * @param userId id of user
	 * @return new BadgeComponent instance
	 * @throws Exception
	 */
	public BadgeComponent buildBadgeComponent(int userId) throws Exception{
		return new BadgeComponent(userId, readBadges(userId));
	}
	
	/**
	 * if the user is online it returns the badgecomponent from the user, if not it creates the BadgeComponent using buildBadgeComponent
	 * @param userId id of user
	 * @return 
	 * @throws Exception
	 */
	public BadgeComponent getBadgeComponent(int userId) throws Exception{
		if(Varoke.getSessionManager().getSessionByUserId(userId) == null)
			return buildBadgeComponent(userId);
		return Varoke.getSessionManager().getSessionByUserId(userId).getHabbo().getBadgeComponent();
	}
	
	/**
	 * Resets ALL Badge slots from an user
	 * @param userId id of user
	 * @throws Exception
	 */
	public void resetSlots(int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE user_badges SET slot=0 WHERE userId=?");
		ps.setInt(1, userId);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	
	/**
	 * updates the slot of the badge
	 * @param badge the badge
	 * @param slot the slod Id
	 * @param userId id of user
	 * @throws Exception
	 */
	public void updateSlot(String badge, int slot, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("UPDATE user_badges SET slot=? WHERE userId=? AND badge=?");
		ps.setInt(1, slot);
		ps.setInt(2, userId);
		ps.setString(3, badge);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
}
