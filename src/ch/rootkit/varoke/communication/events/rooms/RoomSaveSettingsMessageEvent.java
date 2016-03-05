package ch.rootkit.varoke.communication.events.rooms;

//import java.util.ArrayList;
//import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.messages.ClientMessage;
//import ch.rootkit.varoke.habbohotel.chat.SafeChat;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class RoomSaveSettingsMessageEvent implements MessageEvent {

	@Override
	public void handle(Session session, ClientMessage event) throws Exception {
		Room room = Varoke.getGame().getRoomManager().getRoom(event.readInt());
		if(room == null || !room.hasRights(session.getHabbo(), true)){
			return;
		}
		/*String Name = SafeChat.filter(event.readString());
        String Description = SafeChat.filter(event.readString());
        int State = event.readInt(); //0 open; 1 doorbell; 2 password; 3 invisible
        String Password = event.readString();
        int MaxUsers = event.readInt();
        int CategoryId = event.readInt();
        int TagCount = event.readInt();

        List<String> Tags = new ArrayList<String>();
        StringBuilder formattedTags = new StringBuilder();

        for (int i = 0; i < TagCount; i++)
        {
            if (i > 0)
            {
                formattedTags.append(",");
            }

            String tag = event.readString();
            Tags.add(tag);
            formattedTags.append(tag);
        }

        int TradeSettings = event.readInt();//2 everyone; 1 owner; 0 disabled
        int AllowPets = event.readBoolean() ? 1 : 0;
        int AllowPetsEat = event.readBoolean() ? 1 : 0;
        int RoomBlockingEnabled = event.readBoolean() ? 1 : 0;
        int Hidewall = event.readBoolean() ? 1 : 0;
        int WallThickness = event.readInt();
        int FloorThickness = event.readInt();
        int WhoMute = event.readInt(); // mute
        int WhoKick = event.readInt(); // kick
        int WhoBan = event.readInt(); // ban

        int chatMode = event.readInt();
        int chatSize = event.readInt();
        int chatSpeed = event.readInt();
        int chatDistance = event.readInt();
        int extraFlood = event.readInt();
        //TODO EVERYTHING*/
		
	}

}
