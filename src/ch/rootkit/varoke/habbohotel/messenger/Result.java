package ch.rootkit.varoke.habbohotel.messenger;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class Result {
	private int Id;
	private String Username;
	private String Motto;
	private String Look;
	private long LastLogin;
	public Result(int id, String username, String motto, String look, long lastLogin){
		Id = id;
		Username = username;
		Motto = motto;
		Look = look;
		LastLogin = lastLogin;
	}
	public int getId(){ return Id;}
	public String getUsername(){ return Username;}
	public String getMotto(){ return Motto;}
	public String getLook(){ return Look;}
	public long getLastLogin(){ return LastLogin;}
	public void compose(ServerMessage message) throws Exception{
		message.writeInt(getId());
		message.writeString(getUsername());
		message.writeString(getMotto());
		message.writeBoolean(Varoke.getSessionManager().getSessionByUserId(getId()) != null);
		message.writeBoolean(false);
		message.writeString("");
		message.writeInt(0);
		message.writeString(getLook());
		message.writeString(getLastLogin() + "");
	}
}
