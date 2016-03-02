package ch.rootkit.varoke.habbohotel.rooms;

import java.util.Date;
import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.FloorMapMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.HeightMapMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.InitialRoomInfoMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.RoomDataMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.RoomSpacesMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.RoomFloorItemsMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.RoomWallItemsMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.SetRoomUserMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.UpdateUserStatusMessageComposer;
import ch.rootkit.varoke.communication.composers.users.UpdateUserDataMessageComposer;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.threading.executables.TerminateRoom;
import ch.rootkit.varoke.utils.Logger;

public class RoomManager {

	public HashMap<Integer, Room> loadedRooms;
	public HashMap<String, RoomModel> models;
	
	public RoomManager(){
		loadedRooms = new HashMap<Integer, Room>();
		models = new HashMap<String, RoomModel>();
	}
	
	public void initialize() throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Initializing RoomManager ");
		loadedRooms = new HashMap<Integer, Room>();
		models = Varoke.getFactory().getRoomFactory().getRoomModels();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	
	public Room getRoom(int roomId) throws Exception{
		if(loadedRooms.containsKey(roomId))
			return loadedRooms.get(roomId);
		Room r = buildRoom(roomId);
		loadedRooms.put(roomId, r);
		r.startCycle();
		return r;
	}
	
	public Room getLoadedRoom(int roomId) {
		return loadedRooms.get(roomId);
	}
	
	public Room buildRoom(int roomId) throws Exception{
		return Room.fromRoomData(Varoke.getFactory().getRoomFactory().getRoomData(roomId));
	}
	
	public int getUsersInRoom(int roomId){
		if(this.loadedRooms.containsKey(roomId))
			return this.loadedRooms.get(roomId).users();
		return 0;
	}
	
	public RoomModel getModel(String name){
		if(models.containsKey(name))
			return models.get(name);
		return null;
	}
	
	public int size(){
		return loadedRooms.size();
	}
	
	public void checkUnload(Room room) {
		if(room.getRoomUsers().size() == 0){
			Varoke.getGame().getThreadPool().executeScheduled(new TerminateRoom(room), 10000);
		}
	}
	
	public void openRoom(int roomId, Session session) throws Exception{
		if(session.getHabbo().getCurrentRoomId() > 0)
			session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId()).removeFromRoom();
		session.getHabbo().setLoadingRoom(roomId);
		Room r = Varoke.getGame().getRoomManager().getRoom(roomId);
		session.sendMessage(new ServerMessage(Outgoing.get("PrepareRoomMessageComposer")));
		ServerMessage message = new ServerMessage(Outgoing.get("RoomGroupMessageComposer"));
		message.writeInt(0);
		session.sendMessage(message);
		session.sendComposer(new InitialRoomInfoMessageComposer(r));
		if(!r.getData().getWallpaper().equals("0.0"))
			session.sendComposer(new RoomSpacesMessageComposer("wallpaper", r.getData().getWallpaper()));
		if(!r.getData().getFloor().equals("0.0"))
			session.sendComposer(new RoomSpacesMessageComposer("floor", r.getData().getFloor()));
		session.sendComposer(new RoomSpacesMessageComposer("landscape", r.getData().getLandscape()));
		
		message = new ServerMessage(Outgoing.get("RoomRightsLevelMessageComposer"));
		message.writeInt(4);
		session.sendMessage(message);
		session.sendMessage(new ServerMessage(Outgoing.get("HasOwnerRightsMessageComposer")));
		message = new ServerMessage(Outgoing.get("RoomRatingMessageComposer"));
		message.writeInt(r.getData().getScore());
		message.writeBoolean(true); //canvote
		session.sendMessage(message);
		message = new ServerMessage(Outgoing.get("EnableRoomInfoMessageComposer"));
		message.writeInt(roomId);
		session.sendMessage(message);
		message = null;
	}
	
	public void enterRoom(Session session) throws Exception {
		session.sendComposer(new HeightMapMessageComposer(Varoke.getGame().getRoomManager().getRoom(session.getHabbo().getLoadingRoom())));
		session.sendComposer(new FloorMapMessageComposer(Varoke.getGame().getRoomManager().getRoom(session.getHabbo().getLoadingRoom())));
		session.getHabbo().setCurrentRoom(session.getHabbo().getLoadingRoom());
		session.getHabbo().setLoadingRoom(-1);
		RoomUser user = session.getHabbo().getCurrentRoom().getRoomUser(session.getHabbo().getCurrentRoom().addRoomUser(session));
		if(session.getHabbo().getTeleportItem() == null){
			user.setPosition(
					new Point(
							session.getHabbo().getCurrentRoom().getModel().getDoor().getX(),
							session.getHabbo().getCurrentRoom().getModel().getDoor().getY()));
			user.setRotation(session.getHabbo().getCurrentRoom().getModel().getDoor().getRotation());
		}else{
			user.setPosition(
					new Point(
							session.getHabbo().getTeleportItem().getX(),
							session.getHabbo().getTeleportItem().getY()));
			user.setRotation(session.getHabbo().getTeleportItem().getRotation());
		}
		session.getHabbo().getCurrentRoom().sendEveryOneExceptMe(new SetRoomUserMessageComposer(user), user);
		session.getHabbo().getCurrentRoom().sendComposer(new UpdateUserDataMessageComposer(user));
		session.getHabbo().getCurrentRoom().sendComposer(new UpdateUserStatusMessageComposer(user));
		session.sendComposer(new RoomDataMessageComposer(session, user.getRoom().getId(), true));
		session.sendComposer(new RoomFloorItemsMessageComposer(session.getHabbo().getCurrentRoom()));
		session.sendComposer(new RoomWallItemsMessageComposer(session.getHabbo().getCurrentRoom()));
		session.sendComposer(new SetRoomUserMessageComposer(session.getHabbo().getCurrentRoom().getRoomUsers()));
		session.sendComposer(new UpdateUserStatusMessageComposer(session.getHabbo().getCurrentRoom().getRoomUsers()));
		session.getHabbo().getCurrentRoom().sendEveryOneExceptMe(new SetRoomUserMessageComposer(user), user);
	}
	
	public void removeRoom(int roomId){
		loadedRooms.remove(roomId);
	}
}
