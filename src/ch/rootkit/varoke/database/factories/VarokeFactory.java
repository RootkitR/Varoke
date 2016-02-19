package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.landingview.Promotion;

public class VarokeFactory {
	public VarokeFactory(){
		
	}
	public List<String> readUnsafeWords() throws Exception{
		List<String> result = new ArrayList<String>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT word FROM filter WHERE unsafe='1'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(rs.getString("word"));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public HashMap<String,String> readFilter() throws Exception{
		HashMap<String, String> result = new HashMap<String, String>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT word, replacement FROM filter WHERE unsafe='0'");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.put(rs.getString("word"), rs.getString("replacement"));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public List<Promotion> loadLandingView() throws Exception{
		List<Promotion> result = new ArrayList<Promotion>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM landing_view");
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new Promotion(rs.getInt("id"), rs.getString("title"), rs.getString("text"), rs.getString("button_text"), rs.getInt("button_type"), rs.getString("button_link"), rs.getString("image_link")));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
}