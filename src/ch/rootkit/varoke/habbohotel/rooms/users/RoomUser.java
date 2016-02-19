package ch.rootkit.varoke.habbohotel.rooms.users;

import java.util.HashMap;
import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.users.ApplyHanditemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.DanceStatusMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.RoomUserIdleMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.UpdateUserStatusMessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.pathfinding.PathFinder;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.sessions.Session;
public class RoomUser {

	private int VirtualId;
	private Session session;
	private Room room;
	private Point position;
	private Point goal;
	private Point old;
	private int rotation;
	private double z;
	private HashMap<String, String> statusses;
	private boolean isIdle;
	private int carryId;
	private int danceId;
	private int signTimer;
	private PathFinder pathFinder;
	public RoomUser(int virtualId, Session user, Room r){
		VirtualId = virtualId;
		session = user;
		room = r;
		statusses = new HashMap<String, String>();
		statusses.put("flatctrl 4", "");
		goal = new Point(-1,-1);
		old = new Point(-1,-1);
		carryId = 0;
		danceId = 0;
		pathFinder = new PathFinder(r, this);
	}
	public int getVirtualId(){return VirtualId;}
	public Session getClient(){ return session;}
	public Room getRoom(){ return room;}
	public HashMap<String, String> getStatusses(){ return statusses;}
	public Point getPosition() { return position; }
	public Point getGoal() { return goal; }
	public int getRotation(){ return rotation;}
	public double getZ(){ return z;}
	public boolean isIdle(){ return isIdle;}
	
	public int getCarryItem(){ return carryId;}
	public int getSignTimer(){ return signTimer;}
	public PathFinder getPathfinder(){
		return this.pathFinder;
	}
	public void addStatus(String key, String value){
		statusses.put(key, value);
	}
	public void removeStatus(String key){
		statusses.remove(key);
	}
	public void removeAndUpdateStatus(String key){
		removeStatus(key);
		updateStatus();
	}
	public void updateStatus(){
		try {
			getRoom().sendComposer(new UpdateUserStatusMessageComposer(this));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setPathFinderCollection(){
		if(getRoom().getGameMap().isWalkable(this.getGoal().getX(), this.getGoal().getY()))
			this.getPathfinder().findPath();
	}
	public void setPosition(Point p){ position = p; }
	public void setGoal(Point p){ goal = p; }
	public void setRotation(int rot){ rotation = rot;}
	public void setZ(double Z){ z = Z;}
	public void setSignTimer(int time){ signTimer = time;}
	public void exitRoom(){
		ServerMessage DeleteRoomUser;
		try {
			DeleteRoomUser = new ServerMessage(Outgoing.get("UserLeftRoomMessageComposer"));
			DeleteRoomUser.writeString(getVirtualId() + "");
			getRoom().sendMessage(DeleteRoomUser);
			ServerMessage OutOfRoom = new ServerMessage(Outgoing.get("OutOfRoomMessageComposer"));
			getClient().sendMessage(OutOfRoom);
			getRoom().removeRoomUser(this);
			Varoke.getGame().getRoomManager().checkUnload(getRoom());
			getClient().getHabbo().setCurrentRoom(-1);
			this.setGoal(new Point(-1,-1));
			DeleteRoomUser=null;
			OutOfRoom=null;		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void removeFromRoom(){
		ServerMessage DeleteRoomUser;
		try {
			DeleteRoomUser = new ServerMessage(Outgoing.get("UserLeftRoomMessageComposer"));
			DeleteRoomUser.writeString(getVirtualId() + "");
			getRoom().sendMessage(DeleteRoomUser);
			getRoom().removeRoomUser(this);
			this.setGoal(new Point(-1,-1));
			Varoke.getGame().getRoomManager().checkUnload(getRoom());
			DeleteRoomUser=null;
			getClient().getHabbo().setCurrentRoom(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void idle(){
		isIdle = true;
		session.sendComposer(new RoomUserIdleMessageComposer(getVirtualId(), isIdle));
	}
	public void unidle(){
		isIdle = false;
		session.sendComposer(new RoomUserIdleMessageComposer(getVirtualId(), isIdle));
	}
	public void carry(int id) throws Exception{
		carryId = id;
		getRoom().sendComposer(new ApplyHanditemMessageComposer(getVirtualId(), getCarryItem()));
	}
	public void enableDance(int d) throws Exception{
		danceId = d;
		getRoom().sendComposer(new DanceStatusMessageComposer(getVirtualId(), danceId));
	}
	public Point getOldPosition() {
		return old;
	}
	public void setOldPosition(Point position) {
		old = position;
	}
}
