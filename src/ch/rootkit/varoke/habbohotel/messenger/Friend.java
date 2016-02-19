package ch.rootkit.varoke.habbohotel.messenger;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class Friend {
	private int Id;
	private String Username;
	private String Look;
	private String Motto;
	private boolean AppearsOffline;
	private boolean HideInRoom;
	private int friend;
	public Friend(int userId, String username, String look, String motto, boolean appearOffline, boolean hideInRoom, int f){
		Id = userId;
		Username = username;
		Look = look;
		Motto = motto;
		AppearsOffline = appearOffline;
		HideInRoom = hideInRoom;
		friend = f;
	}
	public int getId(){ return Id;}
	public String getUsername(){ return Username;}
	public String getLook(){ return Look;}
	public String getMotto(){ return Motto;}
	public boolean appearOffline(){ return AppearsOffline;}
	public boolean hideInRoom(){ return HideInRoom;}
	public boolean isOnline(){ return Varoke.getSessionManager().getSessionByUserId(getId()) != null && !AppearsOffline;}
	public boolean inRoom(){ return isOnline() ? Varoke.getSessionManager().getSessionByUserId(getId()).getHabbo().getCurrentRoom() != null : false;}
	public Session getSession(){ return Varoke.getSessionManager().getSessionByUserId(getId());}
	public Session getFriend(){ return Varoke.getSessionManager().getSessionByUserId(friend);}
	public void compose(ServerMessage result)throws Exception{
		result.writeInt(getId());
		result.writeString(getUsername());
		result.writeInt(getId() == 0 || isOnline());
		result.writeBoolean(getId() == 0 || isOnline());
		result.writeBoolean(inRoom());
		result.writeString(getLook());
		result.writeInt(0);
		result.writeString(getMotto());
		result.writeString("");
		result.writeString("");
		result.writeBoolean(true);
		result.writeBoolean(false);
		result.writeBoolean(false);
		if(getFriend().getHabbo().getRelationship(getId()) == null)
			result.writeShort((short)0);
		else
			result.writeShort((short)Varoke.getFactory().getRelationshipFactory().getRelation(getFriend().getHabbo().getRelationship(getId()).getType()));
	}
}
