package ch.rootkit.varoke.habbohotel.rooms;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.pathfinding.Door;

public class RoomModel
{
	private String Id;
	private String Heightmap;
	private String PublicItems;
	private String Map="";
	private String RelativeMap="";
	
	private int Divisor;
	private int MapSizeX;
	private int MapSizeY;
	private int MapSize;
	private Door door;
	private boolean ClubOnly;
	
	private int[][] Squares;
	private double[][] SquareHeight;
	private RoomTileState[][] SquareState;
	public RoomModel(String id, String heightMap, String publicItems, int doorX, int doorY, int doorZ, int doorDir, boolean clubOnly, int divisor){
		Id = id;
		Heightmap = heightMap;
		PublicItems = publicItems;
		door = new Door(doorX, doorY, doorDir, doorZ);
		ClubOnly = clubOnly;
		Divisor = divisor;
		String[] tmpHeightmap = getHeightmap().split((char)13 + "");
		MapSizeX = tmpHeightmap[0].length();
		MapSizeY = tmpHeightmap.length;
		MapSize = 0;
		Squares = new int[getSizeX()][getSizeY()];
		SquareHeight = new double[getSizeX()][getSizeY()];
		SquareState = new RoomTileState[getSizeX()][getSizeY()];
		for(int y = 0; y < getSizeY(); y++){
			if(y > 0)
				tmpHeightmap[y] = tmpHeightmap[y].substring(1);
			for (int x = 0; x < getSizeX(); x++) {
				String square = tmpHeightmap[y].substring(x, x + 1).trim()
						.toLowerCase();
				if(square.equals('x')){
					SquareState[x][y] = RoomTileState.CLOSED;
					Squares[x][y] = 1;
				}else if(Varoke.isNumeric(square)){
					SquareState[x][y] = RoomTileState.OPEN;
					Squares[x][y] = 0;
					SquareHeight[x][y] = Double.parseDouble(square);
					MapSize++;
				}
				if(getDoor().getX() == x && getDoor().getY() == y){
					SquareState[x][y] = RoomTileState.OPEN;
					SquareHeight[x][y] = getDoor().getZ();
					RelativeMap += (int)getDoor().getZ() + "";
				}else if(!(square.isEmpty() || square == null)){
					RelativeMap += square;
				}
			}
			RelativeMap += (char) 13;
		}
		for(String line : Heightmap.split("\r\n")){
			if(line.isEmpty() || line == null)
				continue;
			Map += line + ";";
		}
	}
	public String getId(){ return Id;}
	public String getHeightmap(){ return Heightmap;}
	public String getPublicItems(){ return PublicItems;}
	public Door getDoor(){ return door;}
	public boolean clubOnly(){ return ClubOnly;}
	public int getDivisor(){ return Divisor;}
	public int getSizeX(){ return MapSizeX;}
	public int getSizeY(){ return MapSizeY;}
	public int getSize(){ return MapSize;}
	public String getMap(){ return Map;}
	public String getRelativeMap(){ return RelativeMap;}
	public RoomTileState[][] getSquareStates(){ return SquareState;}
	public int[][] getSquares(){ return Squares;}
	public double getHeight(int x, int y)
	{
		return SquareHeight[x][y];
	}
}