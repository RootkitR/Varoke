package ch.rootkit.varoke.habbohotel.sessions;

import java.util.HashMap;

import ch.rootkit.varoke.communication.composers.MessageComposer;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import io.netty.channel.Channel;


public class SessionManager {
	
	private HashMap<Channel, Session> connections;
	private int nextId = 0;
	
	public SessionManager() {
		connections = new HashMap<Channel, Session>();
	}
	
	public void addConnection(Channel c){
		connections.put(c, new Session(getNextId(), c));
	}
	
	public int getNextId(){
		return nextId++;
	}
	
	public void removeSession(Channel c) throws Exception{
		Session session = getSessionByChannel(c);
		connections.remove(c);
		if(session != null && session.getHabbo() != null && session.getHabbo().getCurrentRoomId() > 0){
			session.getHabbo().getCurrentRoom().getRoomUserById(session.getHabbo().getId()).removeFromRoom();
			session.getHabbo().getMessenger().updateMyself();
		}
	}
	
	public Session getSessionByChannel(Channel c){
		if(!connections.containsKey(c))
			return null;
		return connections.get(c);
	}
	
	public Session getSessionByUserId(int id){
		for(Session cn : connections.values()){
			if(cn.getHabbo() != null && cn.getHabbo().getId() == id)
				return cn;
		}
		return null;
	}
	
	public Session getSessionByUsername(String name){
		for(Session cn : connections.values()){
			if(cn.getHabbo() != null && cn.getHabbo().getUsername().equals(name))
				return cn;
		}
		return null;
	}
	
	public void sendToUsersWithFuse(String fuse, MessageComposer composer, int user) throws Exception{
		ServerMessage buffer = composer.compose();
		for(Session cn : connections.values()){
			if(cn.getHabbo() != null && cn.getHabbo().getId() != user && cn.getHabbo().hasFuse(fuse))
				cn.sendMessage(buffer);
		}
		buffer = null;
		composer = null;
	}
	
	public void sendComposerToUsers(Integer[] userIds, MessageComposer composer)throws Exception{
		ServerMessage buffer = composer.compose();
		for(int i : userIds){
			try{
			if(getSessionByUserId(i) != null)
				getSessionByUserId(i).sendMessage(buffer);
			}catch(Exception e){/* WE WON'T BREAK THE STREAK! */}
		}
		buffer = null;
		composer = null;
	}
	
	public void globalComposer(MessageComposer composer) throws Exception{
		ServerMessage buffer = composer.compose();
		for(Session cn : connections.values()){
			try{
			if(cn != null)
				cn.sendMessage(buffer);
			}catch(Exception e){/* WE WON'T BREAK THE STREAK! */}
		}
		buffer = null;
		composer = null;
	}
	
	public int size(){
		return connections.size();
	}
}
