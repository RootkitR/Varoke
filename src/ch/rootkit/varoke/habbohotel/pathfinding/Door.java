package ch.rootkit.varoke.habbohotel.pathfinding;

public class Door {

	private int x;
	private int y;
	private int rot;
	private int z;
	
	public Door(int X, int Y, int Rot, int Z){
		x = X;
		y = Y;
		rot = Rot;
		z = Z;
	}
	
	public int getX(){ 
		return x;
	}
	
	public int getY(){ 
		return y;
	}
	
	public int getRotation(){ 
		return rot;
	}
	
	public int getZ(){ 
		return z;
	}
}
