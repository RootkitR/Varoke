package ch.rootkit.varoke.habbohotel.pathfinding;

public class Point
{
    private int X;
    private int Y;
    
    public int PositionDistance;
    public int ReversedPositionDistance;

    public Point(int x, int y)
    {
        X = x;
        Y = y;
        PositionDistance = 1000;
        ReversedPositionDistance = 1000;
    }
    
    public int getX(){ 
    	return X; 
    }
    public int getY() { 
    	return Y; 
    }
    public boolean equals(Point point) {	
		return (this.X == point.getX() && this.Y == point.getY());
	}
}

