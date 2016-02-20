/*
 * Decompiled with CFR 0_110.
 */
package ch.rootkit.varoke.habbohotel.pathfinding;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;

public class PathFinder {
	
    private Room room;
    private RoomUser roomUser;
    private Queue<Node> path;

    public PathFinder(RoomUser roomUser) {
        this.roomUser = roomUser;
        this.room = null;
    }

    public PathFinder(Room room, RoomUser roomUser) {
        this.room = room;
        this.roomUser = roomUser;
    }

    public void findPath() {
        this.path = this.calculatePath();
    }

    private Queue<Node> calculatePath() {
        GameMap gameMap;
        if (this.room != null && this.roomUser != null && (gameMap = this.room.getGameMap()) != null) {
            Queue<Node> nodeQueue = gameMap.findPath(this.roomUser.getPosition().getX(), this.roomUser.getPosition().getY(), this.roomUser.getGoal().getX(), this.roomUser.getGoal().getY(), this.room);
            if (nodeQueue.size() > 0) {
                try {
                    gameMap.finalize();
                }
                catch (Throwable e) {
                    e.printStackTrace();
                }
                return nodeQueue;
            }
            return new LinkedList<Node>();
        }
        return null;
    }

    public RoomUser getRoomUser() {
        return this.roomUser;
    }

    public void setRoomUnit(RoomUser roomUnit) {
        this.roomUser = roomUnit;
    }

    public Room getRoom() {
        return this.room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Queue<Node> getPath() {
        return this.path;
    }

    public static boolean squareInSquare(Rectangle outerSquare, Rectangle innerSquare) {
        if (outerSquare.x > innerSquare.x) {
            return false;
        }
        if (outerSquare.y > innerSquare.y) {
            return false;
        }
        if (outerSquare.x + outerSquare.width < innerSquare.x + innerSquare.width) {
            return false;
        }
        if (outerSquare.y + outerSquare.height < innerSquare.y + innerSquare.height) {
            return false;
        }
        return true;
    }

    public static boolean pointInSquare(int x1, int y1, int x2, int y2, int pointX, int pointY) {
        return pointX >= x1 && pointY >= y1 && pointX <= x2 && pointY <= y2;
    }

    public static boolean tilesAdjecent(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) <= 1 && Math.abs(y1 - y2) <= 1;
    }

    public static Tile getSquareInFront(int x, int y, int rotation) {
        return PathFinder.getSquareInFront(x, y, rotation, 1);
    }

    public static Tile getSquareInFront(int x, int y, int rotation, int offset) {
        if ((rotation %= 8) == 0) {
            return new Tile(x, y - offset, 0.0);
        }
        if (rotation == 1) {
            return new Tile(x + offset, y - offset, 0.0);
        }
        if (rotation == 2) {
            return new Tile(x + offset, y, 0.0);
        }
        if (rotation == 3) {
            return new Tile(x + offset, y + offset, 0.0);
        }
        if (rotation == 4) {
            return new Tile(x, y + offset, 0.0);
        }
        if (rotation == 5) {
            return new Tile(x - offset, y + offset, 0.0);
        }
        if (rotation == 6) {
            return new Tile(x - offset, y, 0.0);
        }
        if (rotation == 7) {
            return new Tile(x - offset, y - offset, 0.0);
        }
        return new Tile(x, y, 0.0);
    }

    public static List<Tile> getTilesAround(int x, int y) {
        ArrayList<Tile> tiles = new ArrayList<Tile>();
        for (int i = 0; i < 8; ++i) {
            tiles.add(PathFinder.getSquareInFront(x, y, i));
        }
        return tiles;
    }

    public static Rectangle getSquare(int x, int y, int width, int length, int rotation) {
        if ((rotation %= 8) == 2 || rotation == 6) {
            return new Rectangle(x, y, length, width);
        }
        return new Rectangle(x, y, width, length);
    }
}

