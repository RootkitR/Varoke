package ch.rootkit.varoke.habbohotel.sessions;

import java.util.HashMap;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.composers.users.SuperNotificationMessageComposer;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.network.crypto.DiffieHellman;
import ch.rootkit.varoke.network.crypto.RC4;
import io.netty.channel.Channel;

public class Session {

	private int Id;
	private Channel channel;
	private DiffieHellman diffieHellman;
	private RC4 rc4;
	private Habbo habbo;
	private boolean passedHandshake;
	
	public Session(int id, Channel c) {
		Id = id;
		channel = c;
		diffieHellman = new DiffieHellman();
	}
	
	public void setHabbo(Habbo h){
		this.habbo = h;
	}
	
	public int getId(){
		return this.Id;
	}
	
	public Channel getChannel(){
		return this.channel;
	}
	public DiffieHellman getDiffieHellman(){
		return this.diffieHellman;
	}
	
	public boolean passedHandshake(){
		return passedHandshake;
	}
	
	public void setPassedHandshake(boolean b){
		passedHandshake = b;
	}
	
	public void sendMessage(ServerMessage message)throws Exception{
			message.send(channel);
	}
	
	public void sendComposer(MessageComposer composer){
		sendComposer(composer, true);
	}
	
	public void sendComposer(MessageComposer composer, boolean dispose){
		try{
			sendMessage(composer.compose());
			if(dispose)
				composer = null;
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void enableRC4(){
		rc4 = new RC4(getDiffieHellman().getSharedKey().toByteArray());
	}
	
	public RC4 getRC4(){
		return rc4;
	}
	
	public Habbo getHabbo(){
		return habbo;
	}
	
	public void close(){
		channel.close();
	}
	
	public void sendNotif(String text, String image){
		HashMap<String, String> values = new HashMap<String,String>();
		values.put("title","Notification");
		values.put("message",text);
		values.put("linkUrl", "event:");
        values.put("linkTitle", "Close");
        sendComposer(new SuperNotificationMessageComposer(image, values));
		values = null;
	}
	
	public void sendNotif(String text){
		sendNotif(text, "");
	}
}
