package ch.rootkit.varoke.habbohotel.rooms.cycle;

import ch.rootkit.varoke.habbohotel.pathfinding.Node;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.pathfinding.Rotation;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.utils.Logger;

public class WalkCycle extends UserCycle{

	public WalkCycle(Room r) {
		super(r);
	}

	@Override
	public void onCycle(RoomUser roomUser) {
		try{
			if(roomUser.getGoal().getX() != -1 && roomUser.getGoal().getY() != -1){
				onWalk(roomUser);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			Logger.printErrorLine("Error in Room Cycle Task: User Walking");
		}
	}
	public void onWalk(RoomUser roomUser){
		if(roomUser.getPathfinder() == null || roomUser.getPathfinder().getPath() == null)
			return;
		Node nextStep = roomUser.getPathfinder().getPath().poll();
		if(roomUser.getPosition().getX() == roomUser.getGoal().getX() && roomUser.getPosition().getY() == roomUser.getGoal().getY())
		{
			if(roomUser.getPosition().getX() == getRoom().getModel().getDoor().getX() && roomUser.getPosition().getY() == getRoom().getModel().getDoor().getY())
			{

				if(!getRoom().getData().allowWalkthrough())
					getRoom().getGameMap().setWalkable(roomUser.getPosition().getX(), roomUser.getPosition().getY(), true);
				roomUser.exitRoom(); return;
			}
			roomUser.removeAndUpdateStatus("mv");
			roomUser.setOldPosition(new Point(-1,-1));
			roomUser.setGoal(new Point(-1,-1));
			return;
		}
		if(nextStep == null)
		{roomUser.removeAndUpdateStatus("mv");roomUser.setGoal(new Point(-1,-1));return;}
		int rot = Rotation.calculate(roomUser.getPosition().getX(), roomUser.getPosition().getY(), nextStep.getX(), nextStep.getY());
		roomUser.setRotation(rot);
		roomUser.removeStatus("mv");
		roomUser.removeStatus("sit");
		roomUser.removeStatus("lay");
		roomUser.addStatus("mv", nextStep.getX() + "," + nextStep.getY() + "," + getRoom().getItemManager().getHeight(nextStep.getX(),nextStep.getY()));
		roomUser.updateStatus();
		if(!getRoom().getData().allowWalkthrough())
			getRoom().getGameMap().setWalkable(roomUser.getPosition().getX(), roomUser.getPosition().getY(), true);
		roomUser.setOldPosition(roomUser.getPosition());
		roomUser.setPosition(new Point(nextStep.getX(), nextStep.getY()));
		roomUser.setZ(getRoom().getItemManager().getHeight(nextStep.getX(), nextStep.getY()));
		if(!getRoom().getData().allowWalkthrough())
			getRoom().getGameMap().setWalkable(roomUser.getPosition().getX(), roomUser.getPosition().getY(), false);
	}
}
