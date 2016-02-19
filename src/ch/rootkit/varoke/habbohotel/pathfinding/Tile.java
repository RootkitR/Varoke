package ch.rootkit.varoke.habbohotel.pathfinding;
import java.awt.Point;
import java.util.HashSet;

public class Tile extends Point {
	
	private static final long serialVersionUID = 1L;
	public int X;
    public int Y;
    public double Z;

    public Tile() {
        super(0, 0);
        this.X = 0;
        this.Y = 0;
        this.Z = 0.0;
    }

    public Tile(int x, int y, double z) {
        super(x, y);
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Tile && ((Tile)o).X == this.X && ((Tile)o).Y == this.Y;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static HashSet<Tile> getTilesAt(int x, int y, int width, int length, int rotation) {
        HashSet<Tile> pointList = new HashSet<Tile>();
        if (rotation == 0 || rotation == 4) {
            for (int i = x; i <= x + (width - 1); ++i) {
                for (int j = y; j <= y + (length - 1); ++j) {
                    pointList.add(new Tile(i, j, 0.0));
                }
            }
            return pointList;
        } else {
            if (rotation != 2 && rotation != 6) return pointList;
            for (int i = x; i <= x + (length - 1); ++i) {
                for (int j = y; j <= y + (width - 1); ++j) {
                    pointList.add(new Tile(i, j, 0.0));
                }
            }
        }
        return pointList;
    }

    public Tile copy() {
        return new Tile(this.X, this.Y, this.Z);
    }
}

