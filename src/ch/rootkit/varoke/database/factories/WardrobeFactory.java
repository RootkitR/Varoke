package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.sql.PreparedStatement;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.wardrobe.Wardrobe;
import ch.rootkit.varoke.habbohotel.users.wardrobe.WardrobeItem;

public class WardrobeFactory {
	public WardrobeFactory(){
	}
	public Wardrobe buildWardrobe(int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT slot, look, gender FROM user_wardrobe WHERE userId=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		HashMap<Integer, WardrobeItem> items = new HashMap<Integer, WardrobeItem>();
		while(rs.next()){
			items.put(rs.getInt("slot"),new WardrobeItem(
					rs.getInt("slot"),
					rs.getString("look"),
					rs.getString("gender")
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return new Wardrobe(items);
	}
	public void save(int Slot, String Look, String Gender, int userId) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM user_wardrobe WHERE userId=? AND slot=?");
		ps.setInt(1, userId);
		ps.setInt(2, Slot);
		ps.execute();
		ps.close();
		ps = cn.prepareStatement("INSERT INTO user_wardrobe (userId, slot, look, gender) VALUES (?,?,?,?)");
		ps.setInt(1, userId);
		ps.setInt(2, Slot);
		ps.setString(3, Look);
		ps.setString(4, Gender);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
}
