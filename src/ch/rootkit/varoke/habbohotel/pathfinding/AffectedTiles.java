package ch.rootkit.varoke.habbohotel.pathfinding;

import java.util.ArrayList;
import java.util.List;

public class AffectedTiles {
	public static List<Point> getAffectedTiles(int Length, int Width, int PosX, int PosY, int Rotation){
		List<Point> PointList = new ArrayList<Point>();
		if (Length > 1){
			if (Rotation == 0 || Rotation == 4){
				for (int i = 1; i < Length; i++){
					PointList.add(new Point(PosX, PosY + i));
					for (int j = 1; j < Width; j++)
						PointList.add(new Point(PosX + j, PosY + i));
				}
			}else if (Rotation == 2 || Rotation == 6){
				for (int i = 1; i < Length; i++){
					PointList.add(new Point(PosX + i, PosY));
					for (int j = 1; j < Width; j++)
						PointList.add(new Point(PosX + i, PosY + j));
				}
			}
		}
		if (Width > 1){
			if (Rotation == 0 || Rotation == 4){
				for (int i = 1; i < Width; i++){
					PointList.add(new Point(PosX + i, PosY));
					for (int j = 1; j < Length; j++)
						PointList.add(new Point(PosX + i, PosY + j));
				}
			}else if (Rotation == 2 || Rotation == 6){
				for (int i = 1; i < Width; i++){
					PointList.add(new Point(PosX, PosY + i));
					for (int j = 1; j < Length; j++)
						PointList.add(new Point(PosX + j, PosY + i));
				}
			}
		}
		PointList.add(new Point(PosX,PosY));
		return PointList;
	}
}
