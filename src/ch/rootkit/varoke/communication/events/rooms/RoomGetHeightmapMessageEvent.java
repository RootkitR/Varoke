package ch.rootkit.varoke.communication.events.rooms;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.FloorMapMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.HeightMapMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.RoomDataMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.RoomFloorItemsMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.RoomWallItemsMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.SetRoomUserMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.users.UpdateUserStatusMessageComposer;
import ch.rootkit.varoke.communication.composers.users.UpdateUserDataMessageComposer;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.users.RoomUser;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomGetHeightmapMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) {
		try{
		session.sendComposer(new HeightMapMessageComposer(Varoke.getGame().getRoomManager().getRoom(session.getHabbo().getLoadingRoom())));
		session.sendComposer(new FloorMapMessageComposer(Varoke.getGame().getRoomManager().getRoom(session.getHabbo().getLoadingRoom())));
		session.getHabbo().setCurrentRoom(session.getHabbo().getLoadingRoom());
		session.getHabbo().setLoadingRoom(-1);
		RoomUser user = session.getHabbo().getCurrentRoom().getRoomUser(session.getHabbo().getCurrentRoom().addRoomUser(session));
		user.setPosition(
				new Point(
						session.getHabbo().getCurrentRoom().getModel().getDoor().getX(),
						session.getHabbo().getCurrentRoom().getModel().getDoor().getY()));
		user.setRotation(session.getHabbo().getCurrentRoom().getModel().getDoor().getRotation());
		session.getHabbo().getCurrentRoom().sendEveryOneExceptMe(new SetRoomUserMessageComposer(user), user);
		session.getHabbo().getCurrentRoom().sendComposer(new UpdateUserDataMessageComposer(user));
		session.getHabbo().getCurrentRoom().sendComposer(new UpdateUserStatusMessageComposer(user));
		session.sendComposer(new RoomDataMessageComposer(session, user.getRoom().getId(), true));
		session.sendComposer(new RoomFloorItemsMessageComposer(session.getHabbo().getCurrentRoom()));
		session.sendComposer(new RoomWallItemsMessageComposer(session.getHabbo().getCurrentRoom()));
		session.sendComposer(new SetRoomUserMessageComposer(session.getHabbo().getCurrentRoom().getRoomUsers()));
		session.sendComposer(new UpdateUserStatusMessageComposer(session.getHabbo().getCurrentRoom().getRoomUsers()));
		session.getHabbo().getCurrentRoom().sendEveryOneExceptMe(new SetRoomUserMessageComposer(user), user);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
