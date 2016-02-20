package ch.rootkit.varoke.habbohotel.pathfinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.RoomModel;
import ch.rootkit.varoke.habbohotel.rooms.RoomTileState;

public class GameMap {
	
    private final Node[][] nodes;
    private final int width;
    private final int height;
    private List<Node> openList;
    private List<Node> closedList;
    private boolean done = false;
    private Room room;
    
    public GameMap(RoomModel model, Room r) {
        this.nodes = new Node[model.getSizeX()][model.getSizeY()];
        this.width = model.getSizeX() - 1;
        this.height = model.getSizeY() - 1;
        this.initEmptyNodes();
        for(int y = 0; y < model.getSizeY(); y++)
        	for(int x = 0; x < model.getSizeX(); x++)
        		setWalkable(x,y, model.getSquareStates()[x][y] == RoomTileState.OPEN);
        room = r;
    }
    
    public Room getRoom(){
    	return room;
    }
    
    private synchronized void initEmptyNodes() {
        for (int i = 0; i <= this.width; ++i) {
            for (int j = 0; j <= this.height; ++j) {
                this.nodes[i][j] = new Node(i, j);
            }
        }
    }

    public synchronized void setWalkable(int x, int y, boolean bool) {
        if (x > this.nodes.length - 1) {
            return;
        }
        if (y > this.nodes[x].length - 1) {
            return;
        }
        this.nodes[x][y].setWalkable(bool);
        if(getRoom() != null)
        	getRoom().updatePath();
    }
    
	public boolean isWalkable(int x, int y) {
		if (x > this.nodes.length - 1) {
            return false;
        }
        if (y > this.nodes[x].length - 1) {
            return false;
        }
        return this.nodes[x][y].isWalkable();
	}
	
    public final Node getNode(int x, int y) {
        return this.nodes[x][y];
    }

    public final synchronized List<Node> getNodes() {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int x = 0; x < this.nodes.length; ++x) {
            for (int y = 0; y < this.nodes[x].length; ++y) {
                nodes.add(this.getNode(x, y));
            }
        }
        return nodes;
    }

    public final synchronized Queue<Node> findPath(int oldX, int oldY, int newX, int newY, Room room) {
        if (oldX == newX && oldY == newY) {
            return new LinkedList<Node>();
        }
        this.openList = new LinkedList<Node>();
        this.closedList = new LinkedList<Node>();
        if (oldX > this.width || oldY > this.height || newX > this.width || newY > this.height) {
            return new LinkedList<Node>();
        }
        this.openList.add(this.nodes[oldX][oldY]);
        this.done = false;
        while (!this.done) {
        	Node current = this.lowestFInOpen();
            this.closedList.add(current);
            this.openList.remove(current);
            if (current.getX() == newX && current.getY() == newY && Math.abs(room.getModel().getHeight(current.getX(), current.getY()) - room.getModel().getHeight(newX, newY)) <= 3) {
                return this.calcPath(this.nodes[oldX][oldY], current, room);
            }
            List<Node> adjacentNodes = this.getAdjacent(current, newX, newY, room);
            for (Node currentAdj : adjacentNodes) {
                if (Math.abs(room.getModel().getHeight(current.getX(), current.getY()) - room.getModel().getHeight(newX, newY)) > 3) continue;
                if (!this.openList.contains(currentAdj) || currentAdj.getX() == newX && currentAdj.getY() == newY) {
                    currentAdj.setPrevious((Node)current);
                    currentAdj.sethCosts((Node)this.nodes[newX][newY]);
                    currentAdj.setgCosts((Node)current);
                    this.openList.add((Node)currentAdj);
                    continue;
                }
                if (currentAdj.getgCosts() <= currentAdj.calculategCosts((Node)current)) continue;
                currentAdj.setPrevious((Node)current);
                currentAdj.setgCosts((Node)current);
            }
            if (!this.openList.isEmpty()) continue;
            return new LinkedList<Node>();
        }
        return null;
    }

    private Queue<Node> calcPath(Node start,Node goal, Room room) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node curr = goal;
        boolean done = false;
        while (!done) {
            if (curr == null) continue;
            path.addFirst(curr);
            if ((curr = this.getNode(curr.getPrevious().getX(), curr.getPrevious().getY())) == null || start == null || !curr.equals(start)) continue;
            done = true;
        }
        return path;
    }

    private synchronized Node lowestFInOpen() {
        if (this.openList == null) {
            return null;
        }
        Node cheapest = (Node)this.openList.get(0);
        for (Node anOpenList : this.openList) {
            if (anOpenList.getfCosts() >= cheapest.getfCosts()) continue;
            cheapest = anOpenList;
        }
        return cheapest;
    }

    private synchronized List<Node> getAdjacent(Node node, int newX, int newY, Room room) {
    	Node temp;
        int x = node.getX();
        int y = node.getY();
        LinkedList<Node> adj = new LinkedList<Node>();
        if (x > 0 && ((temp = this.getNode(x - 1, y)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY)) {
            temp.setIsDiagonaly(false);
            adj.add(temp);
        }
        if (x < this.width && ((temp = this.getNode(x + 1, y)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY)) {
            temp.setIsDiagonaly(false);
            adj.add(temp);
        }
        if (y > 0 && ((temp = this.getNode(x, y - 1)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY)) {
            temp.setIsDiagonaly(false);
            adj.add(temp);
        }
        if (y < this.height && ((temp = this.getNode(x, y + 1)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY)) {
            temp.setIsDiagonaly(false);
            adj.add(temp);
        }
        if (x < this.width && y < this.height && ((temp = this.getNode(x + 1, y + 1)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY )) {
            temp.setIsDiagonaly(true);
            adj.add(temp);
        }
        if (x > 0 && y > 0 && ((temp = this.getNode(x - 1, y - 1)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY)) {
            temp.setIsDiagonaly(true);
            adj.add(temp);
        }
        if (x > 0 && y < this.height && ((temp = this.getNode(x - 1, y + 1)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY )) {
            temp.setIsDiagonaly(true);
            adj.add(temp);
        }
        if (x < this.width && y > 0 && ((temp = this.getNode(x + 1, y - 1)).isWalkable() && !this.closedList.contains(temp) || temp.getX() == newX && temp.getY() == newY )) {
            temp.setIsDiagonaly(true);
            adj.add(temp);
        }
        return adj;
    }

    public void finalize() throws Throwable {
        super.finalize();
    }
}

