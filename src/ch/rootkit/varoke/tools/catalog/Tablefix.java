package ch.rootkit.varoke.tools.catalog;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import ch.rootkit.varoke.database.Database;
import ch.rootkit.varoke.utils.Configuration;

public class Tablefix {

	private static Database db;
	private static FurniDataParser fdp;
	public static void start() throws Exception{
		Configuration.initialize();
		db = new Database();
		fdp = new FurniDataParser();
		fdp.parseXmlFile();
		fdp.parseDocument();
		Connection cn = getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM furnitures");
		ResultSet rs = ps.executeQuery();
		int failed = 0;
		int success = 0;
		while(rs.next()){
			FurniData furni = getParser().getFurni(rs.getString("item_name"));
			if(furni == null){
				System.out.println("Furni " + rs.getString("item_name") + " not found!");
				PreparedStatement ps3 = cn.prepareStatement("DELETE FROM furnitures WHERE id=?");
				ps3.setInt(1, rs.getInt("id"));
				ps3.execute();
				ps3.close();
				ps3 = cn.prepareStatement("DELETE FROM catalog_items WHERE item_ids=?");
				ps3.setString(1, rs.getInt("id") + "");
				ps3.execute();
				ps3.close();
				ps3 = null;
				failed++;
				continue;
			}
			PreparedStatement ps2 = cn.prepareStatement("UPDATE furnitures SET sprite_id=?, x=?, y=?, can_sit=?, can_walk=?, public_name=? WHERE id=?");
			ps2.setInt(1, furni.getId());
			ps2.setInt(2, furni.getX());
			ps2.setInt(3, furni.getY());
			ps2.setString(4, furni.canSit() ? "1" : "0");
			ps2.setString(5, furni.canWalk() ? "1" : "0");
			ps2.setString(6, furni.getName());
			ps2.setInt(7, rs.getInt("id"));
			ps2.execute();
			ps2.close();
			ps2 = null;
			success++;
		}
		ps.close();
		rs.close();
		ps = cn.prepareStatement("SELECT * FROM catalog_items");
		rs = ps.executeQuery();
		while(rs.next()){
			PreparedStatement ps2 = cn.prepareStatement("SELECT * FROM furnitures WHERE id=?");
			ps2.setInt(1, Integer.parseInt(rs.getString("item_ids")));
			ResultSet rs3 = ps2.executeQuery();
			if(rs3.next()){
				continue;
			}else{
				PreparedStatement ps3 = cn.prepareStatement("DELETE FROM catalog_items WHERE item_ids=?");
				ps3.setString(1, rs.getString("item_ids"));
				ps3.execute();
				ps3.close();
			}
			rs3.close();
			ps2.close();
		}
		cn.close();
		ps.close();
		rs.close();
		
		System.out.println("Finished : " + failed + " failed, " + success + " successfull!");
	}
	public static Database getDatabase(){
		return db;
	}
	public static FurniDataParser getParser(){
		return fdp;
	}
}
