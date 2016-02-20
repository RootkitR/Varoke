package ch.rootkit.varoke.habbohotel.rooms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.pathfinding.GameMap;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.cycle.RoomCycle;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItemManager;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class Room {

	private RoomData data;
	private HashMap<Integer, RoomUser> users;
	private List<Integer> userWithRights;
	private RoomCycle cycle;
	private GameMap gameMap;
	private RoomItemManager roomItemManager;
	
	public Room(RoomData rd) throws Exception{
		data = rd;
		users = new HashMap<Integer, RoomUser>();
		gameMap = new GameMap(getModel(), this);
		userWithRights = Varoke.getFactory().getRoomFactory().getUserWithRights(getId());
		roomItemManager = Varoke.getFactory().getRoomFactory().createRoomItemManager(this);
		for(RoomItem roomItem : roomItemManager.getFloorItems())
			for(Point tile : roomItem.getAffectedTiles())
				gameMap.setWalkable(tile.getX(), tile.getY(), getItemManager().canWalk(tile.getX(), tile.getY()));
	}
	
	public GameMap getGameMap() { 
		return gameMap; 
	}
	
	public static Room fromRoomData(RoomData rd) throws Exception{
		return new Room(rd);
	}
	
	public int getId(){
		return getData().getId();
	}
	
	public RoomData getData(){
		return data;
	}
	
	public RoomModel getModel(){
		return Varoke.getGame().getRoomManager().getModel(data.getModelName());
	}
	
	public int getNextVirtualId(){
		int i = 90;
		while(users.containsKey(i))
			i++;
		return i;
	}
	
	public List<RoomUser> getRoomUsers(){
		return new ArrayList<RoomUser>(users.values());
	}
	
	public RoomUser getRoomUser(int virtualId){
		return users.get(virtualId);
	}
	
	public RoomItemManager getItemManager(){
		return roomItemManager;
	}
	
	public RoomUser getRoomUserById(int userId){
		for(RoomUser r : users.values()){
			if(r != null && r.getClient() != null && r.getClient().getHabbo() != null && r.getClient().getHabbo().getId() == userId){
				return r;}
		}
		return null;
	}
	
	public int users(){
		return this.users.size();
	}
	
	public int addRoomUser(Session Session){
		int virtId = getNextVirtualId();
		this.users.put(virtId, new RoomUser(virtId, Session, this));
		return virtId;
	}
	
	public void removeRoomUser(RoomUser user){
		this.users.remove(user.getVirtualId());
	}
	
	public void sendComposer(MessageComposer composer) throws Exception{
		ServerMessage result = composer.compose();
		for(RoomUser roomUser : users.values()){
			if(roomUser.getClient() != null && roomUser.getClient().getChannel() != null && roomUser.getClient().getChannel().isActive()){
				try{
				roomUser.getClient().sendMessage(result);
				}catch(Exception ex){/* MAYBE NOT IN ROOM ANYMORE*/}
			}
		}
		result = null;
		composer = null;
	}
	
	public void sendMessage(ServerMessage message){
		for(RoomUser roomUser : users.values()){
			if(roomUser.getClient() != null && roomUser.getClient().getChannel() != null && roomUser.getClient().getChannel().isActive()){
				try{
				roomUser.getClient().sendMessage(message);
				}catch(Exception ex){/* MAYBE NOT IN ROOM ANYMORE*/}
			}
		}
	}
	
	public void sendEveryOneExceptMe(MessageComposer composer, RoomUser session) throws Exception{
		ServerMessage result = composer.compose();
		for(RoomUser roomUser : users.values()){
			if(roomUser != null && roomUser.getClient() != null && roomUser.getClient().getChannel() != null && roomUser.getClient().getHabbo() != null && roomUser.getClient().getChannel().isActive() && roomUser.getClient().getHabbo().getId() != session.getClient().getHabbo().getId()){
				try{
				roomUser.getClient().sendMessage(result);
				}catch(Exception ex){/* MAYBE NOT IN ROOM ANYMORE*/}
			}
		}
		result = null;
		composer = null;
	}
	
	public void startCycle(){
		cycle = new RoomCycle(this);
		Varoke.getGame().getThreadPool().executeScheduled(cycle, 500);
	}
	
	public void dispose(){
		cycle.stop();
		this.users.clear();
		this.users = null;
	}
	
	public boolean hasRights(Habbo habbo, boolean adminRights){
		if(habbo.getId() == getData().getOwner())
			return true;
		if(this.userWithRights.contains(habbo.getId()) && !adminRights)
			return true;
		if(habbo.hasFuse("rights_anyroom"))
			return true;
		return false;
	}
	
	public void updatePath() {
		for(RoomUser roomUser : users.values()){
			if(roomUser != null && roomUser.getGoal().getX() != -1 && roomUser.getGoal().getX() != -1)
				roomUser.setPathFinderCollection();
		}
	}
}
