package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.navigator.FlatCat;
import ch.rootkit.varoke.habbohotel.navigator.NavigatorCategory;
import ch.rootkit.varoke.habbohotel.navigator.NavigatorSearch;
import ch.rootkit.varoke.habbohotel.rooms.RoomData;

public class NavigatorFactory {
	public List<RoomData> readPublicRooms(int category) throws Exception{
		List<RoomData> publicRooms = new ArrayList<RoomData>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT roomId FROM navigator_publics WHERE categoryId=?");
		ps.setInt(1, category);
		ResultSet rs = ps.executeQuery();
		while(rs.next())
		{
			publicRooms.add(Varoke.getFactory().getRoomFactory().getRoomData(rs.getInt("roomId")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return publicRooms;
	}
	public List<NavigatorCategory> readCategories() throws Exception{
		List<NavigatorCategory> categories = new ArrayList<NavigatorCategory>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM navigator_categories");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			categories.add(new NavigatorCategory(
					rs.getInt("id"),
					rs.getString("title"),
					Varoke.stringToBool(rs.getString("collapsed")),
					Varoke.stringToBool(rs.getString("thumbnail")),
					readPublicRooms(rs.getInt("id"))));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return categories;
	}
	public List<FlatCat> readFlatCats() throws Exception{
		List<FlatCat> cats = new ArrayList<FlatCat>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM navigator_flatcats");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			cats.add(new FlatCat(
					rs.getInt("id"),
					rs.getString("caption"),
					rs.getInt("min_rank")
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return cats;
	}
	public List<NavigatorSearch> readUserSearches(int userId) throws Exception{
		List<NavigatorSearch> result = new ArrayList<NavigatorSearch>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM navigator_search WHERE userId=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new NavigatorSearch(
					rs.getInt("id"),
					rs.getString("Value1"),
					rs.getString("search")
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public NavigatorSearch createUserSearch(int userId, String value1, String searchText) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO navigator_search (userId, value1, search) VALUES (?,?,?);");
		ps.setInt(1, userId);
		ps.setString(2, value1);
		ps.setString(3, searchText);
		ps.execute();
		ps.close();
		ResultSet rs = Varoke.getDatabase().getConnection().prepareStatement("SELECT LAST_INSERT_ID();").executeQuery();
		NavigatorSearch ns = null;
		if(rs.next())
			ns = new NavigatorSearch(rs.getInt(1), value1, searchText);
		Varoke.getFactory().dispose(cn, ps, rs);
		return ns;
	}
	public void deleteUserSearch(int id) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM navigator_search WHERE id=?");
		ps.setInt(1, id);
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
}
