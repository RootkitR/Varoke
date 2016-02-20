package ch.rootkit.varoke.habbohotel.rooms;

import java.util.Date;
import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
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
			loadedRooms.remove(room.getId());
			room.dispose();
			room = null;
		}
	}
}
