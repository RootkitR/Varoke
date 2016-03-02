package ch.rootkit.varoke.habbohotel.rooms.cycle;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.pathfinding.Rotation;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.threading.executables.UpdateItemAction;

public class TeleportCycle extends UserCycle{

	public TeleportCycle(Room r) {
		super(r);
	}
	
	@Override
	public void onCycle(RoomUser roomUser) {
		try{
			if(roomUser.getClient().getHabbo().getTeleportItem() != null){
				RoomItem teleporter = roomUser.getClient().getHabbo().getTeleportItem();
				int otherTeleId = Varoke.getFactory().getInventoryFactory().getOtherTele(teleporter.getId());
				int otherRoomId = Varoke.getFactory().getInventoryFactory().getRoomByItem(otherTeleId);
				switch(roomUser.getClient().getHabbo().getTeleportStep()){
					case 1 :
					{
						roomUser.setGoal(new Point(teleporter.getX(), roomUser.getClient().getHabbo().getTeleportItem().getY()));
				        roomUser.setRotation(Rotation.calculate(roomUser.getPosition().getX(),roomUser.getPosition().getY(), teleporter.getX(), teleporter.getY()));
				        roomUser.addStatus("mv", "" + teleporter.getX() + "," + teleporter.getY() + "," + teleporter.getZ());
				        roomUser.updateStatus();
				        roomUser.setPosition(new Point(teleporter.getX(), teleporter.getY()));
				        roomUser.setZ(teleporter.getZ());
				        roomUser.removeStatus("mv");
				        teleporter.setExtraData("0");
						break;
					}
					case 2:
					{
						teleporter.update();
						break;
					}
					case 3:
					{
						teleporter.setExtraData("2");
						teleporter.update();
				        if (otherRoomId == 0 || otherTeleId == 0) {
				        	roomUser.getClient().getHabbo().setTeleportStep(roomUser.getClient().getHabbo().getTeleportStep() + 1);
				        }
				        break;
					}
					case 4:
					{
						Room targetRoom = getRoom();
				        if (getRoom().getId() != otherRoomId) {
				            targetRoom = Varoke.getGame().getRoomManager().getRoom(otherRoomId);
				        }
				        if (targetRoom == null) {
				        	System.err.println("null room");
				            return;
				        }
				        RoomItem targetTeleport = targetRoom.getItemManager().getItem(otherTeleId);
				        if (targetTeleport == null) {
				        	System.err.println("null item");
				            return;
				        }
				        if (targetRoom != getRoom()) {
				            roomUser.removeFromRoom();
				            Varoke.getGame().getRoomManager().openRoom(targetRoom.getId(), roomUser.getClient());
				            roomUser.getClient().getHabbo().setTeleportItem(targetTeleport);
				            roomUser.getClient().getHabbo().setTeleportStep(5);
				        }else{
				        	roomUser.setPosition(new Point(targetTeleport.getX(), targetTeleport.getY()));
				        	roomUser.setRotation(targetTeleport.getRotation());
				        	roomUser.getClient().getHabbo().setTeleportItem(targetTeleport);
				        	roomUser.updateStatus();
				        }
				        targetTeleport.setExtraData("2");
				        targetTeleport.update();
				        teleporter.setExtraData("0");
				        Varoke.getGame().getThreadPool().executeScheduled(new UpdateItemAction(teleporter, "0"), 500);
						break;
					}
					case 5:
					{
						teleporter.setExtraData("1");
						teleporter.update();
						break;
					}
					case 6:
					{
						roomUser.getClient().getHabbo().setTeleportItem(null);
						roomUser.getClient().getHabbo().setTeleportStep(0);
						roomUser.setGoal(teleporter.inFront());
						roomUser.setPathFinderCollection();
						Varoke.getGame().getThreadPool().executeScheduled(new UpdateItemAction(teleporter, "0"), 1000);
						break;
					}
				}
				roomUser.getClient().getHabbo().setTeleportStep(roomUser.getClient().getHabbo().getTeleportStep() + 1);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}
