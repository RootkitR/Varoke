package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.relationships.Relation;
import ch.rootkit.varoke.habbohotel.relationships.Relationship;

public class RelationshipFactory {
	public RelationshipFactory(){
	}
	public Relation getRelation(int i){
		switch(i){
		case 1:
			return Relation.LOVE;
		case 2:
			return Relation.FRIEND;
		case 3:
			return Relation.HATE;
		case 0:
		default:
			return Relation.NONE;
		}
	}
	public int getRelation(Relation r){
		switch(r){
		case LOVE:
			return 1;
		case FRIEND:
			return 2;
		case HATE:
			return 3;
		case NONE:
		default:
			return 0;
		}
	}
	public List<Relationship> readRelationships(int userId) throws Exception{
		List<Relationship> result = new ArrayList<Relationship>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT `to`,`state` FROM relationships WHERE `from`=?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new Relationship(
					userId,
					rs.getInt("to"),
					getRelation(rs.getInt("state"))
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public void deleteRelationship(Relationship r) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("DELETE FROM relationships WHERE `to`=? AND `from`=?");
		ps.setInt(1, r.getTo());
		ps.setInt(2, r.getFrom());
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void createRelationship(Relationship r) throws Exception{
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("INSERT INTO relationships (`from`,`to`,`state`) VALUES (?,?,?)");
		ps.setInt(1, r.getFrom());
		ps.setInt(2,  r.getTo());
		ps.setInt(3, getRelation(r.getType()));
		ps.execute();
		Varoke.getFactory().dispose(cn, ps, null);
	}
	public void updateRelationship(Relationship r) throws Exception{
		deleteRelationship(r);
		createRelationship(r);
	}
}
