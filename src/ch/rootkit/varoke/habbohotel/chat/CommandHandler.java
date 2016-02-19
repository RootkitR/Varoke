package ch.rootkit.varoke.habbohotel.chat;

import java.util.Date;
import java.util.HashMap;

import org.joda.time.Period;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.catalog.PublishShopMessageComposer;
import ch.rootkit.varoke.communication.composers.users.SuperNotificationMessageComposer;
import ch.rootkit.varoke.habbohotel.rooms.items.RoomItem;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class CommandHandler {
	public static boolean handled(Session Session, String Message) throws Exception{
		String[] args = Message.split(" ");
		args[0] = args[0].substring(1);
		switch(args[0]){
			case "about":
			{
				HashMap<String, String> values = new HashMap<String,String>();
				values.put("title", "Varoke Emulator");
				Period p = new Period(Varoke.serverReady, new Date().getTime());
				String Uptime = "Online seit " + p.getDays() + " Tage, " + p.getHours() + " Stunden und " + p.getMinutes() + " Minuten.";
				String activeRoomsandUsers = "Es sind " + Varoke.getSessionManager().size() + " Habbos online und " + Varoke.getGame().getRoomManager().size() + " Räume aktiv."; 
				String ramUsage = "<b>RAM-Usage</b>" + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 0x100000) + "/" + Runtime.getRuntime().freeMemory() / 0x100000 + "MB\r" + "<b>CPU Cores: </b>" + Runtime.getRuntime().availableProcessors();
				values.put("message", "<div align=\"center\"><b><font color=\"#000000\" size=\"18\">Varoke Emulator</font></b><br>Developers:<br>- Rootkit<br>Current Version:<br>" + Varoke.Version + "<br>" + Uptime + "<br/>" + activeRoomsandUsers + "</div>\n\n" + ramUsage);
				values.put("linkUrl", "event:");
	            values.put("linkTitle", "Close");
				Session.sendComposer(new SuperNotificationMessageComposer("Varoke", values));
				values = null;
				return true;
			}
			case "miute":
			{
				if(!Session.getHabbo().hasFuse("cmd_mute"))
					return false;
				
				Session user = Varoke.getSessionManager().getSessionByUsername(args[1]);
				if(user == null){
					Session.sendNotif("User %u not found!".replace("%u", args[1]));
					return true;
				}else if(user.getHabbo().getUsername().equals(Session.getHabbo().getUsername())){
					Session.sendNotif("Why do you want to mute yourself?");
					return true;
				}
				user.getHabbo().mute(true);
				user.sendNotif("%u muted you!".replace("%u", Session.getHabbo().getUsername()));
				return true;
			}
			case "unmute":
			{
				if(!Session.getHabbo().hasFuse("cmd_mute"))
					return false;
				Session user = Varoke.getSessionManager().getSessionByUsername(args[1]);
				if(user == null){
					Session.sendNotif("User %u not found!".replace("%u", args[1]));
					return true;
				}
				user.getHabbo().mute(false);
				user.sendNotif("%u unmuted you!".replace("%u", Session.getHabbo().getUsername()));
				return true;
			}
			case "badge":
			{
				if(!Session.getHabbo().hasFuse("cmd_badge"))
					return false;
				Session user = Varoke.getSessionManager().getSessionByUsername(args[1]);
				if(user == null){
					Session.sendNotif("User %u not found!".replace("%u", args[1]));
					return true;
				}
				if(!user.getHabbo().getBadgeComponent().hasBadge(args[2])){
					user.getHabbo().getBadgeComponent().addBadge(args[2]);
					Session.sendNotif("Badge sucessfully sent!");
					user.sendNotif("You've recieved a Badge!");
				}else
					Session.sendNotif("%u already has this Badge!".replace("%u", args[1]));
				
				return true;
			}
			case "takebadge":
			{
				if(!Session.getHabbo().hasFuse("cmd_badge"))
					return false;
				Session user = Varoke.getSessionManager().getSessionByUsername(args[1]);
				if(user == null){
					Session.sendNotif("User %u not found!".replace("%u", args[1]));
					return true;
				}
				if(user.getHabbo().getBadgeComponent().hasBadge(args[2])){
					user.getHabbo().getBadgeComponent().removeBadge(args[2]);
					Session.sendNotif("Badge sucessfully removed!");
					user.sendNotif("%u took a Badge!".replace("%u", Session.getHabbo().getUsername()));
				}else
					Session.sendNotif("%u doesn't own this Badge!".replace("%u", args[1]));
				return true;
			}
			case "update_catalog":
			{
				Varoke.getGame().refreshCatalog();
				Varoke.getSessionManager().globalComposer(new PublishShopMessageComposer());
				return true;
			}
			case "pickall":
			{
				if(Session.getHabbo().getCurrentRoom().hasRights(Session.getHabbo(), true)){
					for(RoomItem item : Session.getHabbo().getCurrentRoom().getItemManager().getFloorItems()){
						Session.getHabbo().getCurrentRoom().getItemManager().removeItem(item.getId(), Session.getHabbo().getId());	
						Session.getHabbo().getInventory().addItem(item.getId(), item.getBaseItemId(), item.getExtraData(), item.getLimitedId(), item.getGroupId(), item.isBuildersFurni());
					}
					for(RoomItem item : Session.getHabbo().getCurrentRoom().getItemManager().getWallItems()){
						Session.getHabbo().getCurrentRoom().getItemManager().removeItem(item.getId(), Session.getHabbo().getId());	
						Session.getHabbo().getInventory().addItem(item.getId(), item.getBaseItemId(), item.getExtraData(), item.getLimitedId(), item.getGroupId(), item.isBuildersFurni());

					}
				}
				return true;
			}
		}
		return false;
	}
	public static void storeMessage(int fromUser, int toUser, int roomId, String Message, String Type) throws Exception{
		Varoke.getFactory().getMessengerFactory().store(fromUser, toUser, roomId, Message, Type);
	}
}
