package ch.rootkit.varoke.habbohotel.relationships;

public class Relationship {

	private int From;
	private int To;
	private Relation Type;
	
	public Relationship(int from, int to, Relation type){
		From = from;
		To = to;
		Type = type;
	}
	
	public int getFrom(){ 
		return From;
	}
	
	public int getTo(){ 
		return To;
	}
	
	public Relation getType(){
		return Type;
	}
}
