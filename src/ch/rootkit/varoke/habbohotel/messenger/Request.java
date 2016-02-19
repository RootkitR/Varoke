package ch.rootkit.varoke.habbohotel.messenger;

import ch.rootkit.varoke.communication.messages.ServerMessage;

public class Request {
	private int Id;
	private String Username;
	private String Look;
	public Request(int id, String username, String look){
		Id = id;
		Username = username;
		Look = look;
	}
	public int getId(){ return Id;}
	public String getUsername(){ return Username;}
	public String getLook(){ return Look;}
	public void compose(ServerMessage result) throws Exception{
		result.writeInt(getId());
		result.writeString(getUsername());
		result.writeString(getLook());
	}
}
