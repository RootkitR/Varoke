package ch.rootkit.varoke.habbohotel.messenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.mysql.jdbc.StringUtils;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.messenger.ConsoleChatErrorMessageComposer;
import ch.rootkit.varoke.communication.composers.messenger.ConsoleChatMessageComposer;
import ch.rootkit.varoke.communication.composers.messenger.ConsoleSearchFriendMessageComposer;
import ch.rootkit.varoke.communication.composers.messenger.ConsoleSendFriendRequestMessageComposer;
import ch.rootkit.varoke.communication.composers.messenger.FriendUpdateMessageComposer;
import ch.rootkit.varoke.habbohotel.chat.CommandHandler;
import ch.rootkit.varoke.habbohotel.chat.SafeChat;
import ch.rootkit.varoke.habbohotel.messenger.offline.OfflineMessage;
import ch.rootkit.varoke.habbohotel.sessions.Session;

public class Messenger {

	private HashMap<Integer, Friend> friends;
	private HashMap<Integer, Request> requests;
	private int User;
	private boolean initialized;
	public Messenger(int user, HashMap<Integer, Friend> f, HashMap<Integer, Request> r){
		friends = f;
		requests = r;
		User = user;
	}
	public HashMap<Integer, Friend> getFriends(){ return friends;}
	public HashMap<Integer, Request> getRequests(){ return requests;}
	
	public void removeFriend(int userId, boolean updateOther) throws Exception{
		if(friends.containsKey(userId)){
			Varoke.getFactory().getMessengerFactory().removeFriend(User, userId);
			getSession().sendComposer(new FriendUpdateMessageComposer(FriendUpdateType.REMOVE,friends.get(userId)));
			Session cn = Varoke.getSessionManager().getSessionByUserId(userId);
			friends.remove(userId);
			if(cn != null && cn.getHabbo() != null && cn.getHabbo().getMessenger() != null && updateOther)
				cn.getHabbo().getMessenger().removeFriend(User, false);
		}
	}
	public void acceptRequest(int userId) throws Exception{
		if(requests.containsKey(userId)){
			requests.remove(userId);
			Varoke.getFactory().getMessengerFactory().removeRequest(userId, User);
			Varoke.getFactory().getMessengerFactory().addFriend(userId, User);
			friends.put(userId, Varoke.getFactory().getMessengerFactory().buildFriend(userId, User));
			updateFriend(userId);
			Session cn = Varoke.getSessionManager().getSessionByUserId(userId);
			if(cn != null && cn.getHabbo() != null && cn.getHabbo().getMessenger() != null)
				cn.getHabbo().getMessenger().addFriend(User);
		}
	}
	public void addFriend(int userId) throws Exception{
		if(!friends.containsKey(userId) && !requests.containsKey(userId)){
			friends.put(userId, Varoke.getFactory().getMessengerFactory().buildFriend(userId, User));
			updateFriend(userId);
		}
	}
	public void declineRequest(int userId) throws Exception{
		if(requests.containsKey(userId)){
			Varoke.getFactory().getMessengerFactory().removeRequest(userId, User);
			requests.remove(userId);
		}
	}
	public void declineAllRequests() throws Exception{
		Varoke.getFactory().getMessengerFactory().removeAllRequests(User);
		requests.clear();
	}
	public void createRequest(int To) throws Exception{
		if(friends.containsKey(To) || requests.containsKey(To) || Varoke.getFactory().getMessengerFactory().requestExists(User, To)) return;
		Varoke.getFactory().getMessengerFactory().createRequest(User, To);
		if(Varoke.getSessionManager().getSessionByUserId(To) != null)
			Varoke.getSessionManager().getSessionByUserId(To).getHabbo().getMessenger().addRequest(User);
	}
	public void addRequest(int userId)throws Exception{
		if(friends.containsKey(userId) || requests.containsKey(userId)) return;
		Request r = Varoke.getFactory().getMessengerFactory().buildRequest(userId);
		this.requests.put(userId, r);
		getSession().sendComposer(new ConsoleSendFriendRequestMessageComposer(r));
	}
	public void updateFriend(int userId)throws Exception{
		if(isInitialized())
			getSession().sendComposer(new FriendUpdateMessageComposer(FriendUpdateType.UPDATE, getFriends().get(userId)));
	}
	public Session getSession(){
		return Varoke.getSessionManager().getSessionByUserId(User);
	}
	public void updateMyself()throws Exception{
		for(Friend f : getFriends().values()){
			if(f != null && f.getSession() != null){
				try{
					f.getSession().getHabbo().getMessenger().updateFriend(User);
				}catch(Exception ex){/*MAYBE SOME CONNECTION ERRORS*/}
			}
		}
	}
	public void handleMessage(int to, String message) throws Exception{
		if(!getFriends().containsKey(to)){
			getSession().sendComposer(new ConsoleChatErrorMessageComposer(6, to));
			return;
		}
		if(getSession().getHabbo().isMuted()){
			getSession().sendComposer(new ConsoleChatErrorMessageComposer(4, to));
			return;
		}
		if(message.isEmpty() || StringUtils.isEmptyOrWhitespaceOnly(message) || StringUtils.isNullOrEmpty(message))
			return;
		if(getFriends().get(to).getSession() != null && SafeChat.isSafe(message) && to > 0){
			if(getFriends().get(to).getSession().getHabbo().isMuted())
				getSession().sendComposer(new ConsoleChatErrorMessageComposer(3,to));
			getFriends().get(to).getSession().sendComposer(new ConsoleChatMessageComposer(User, SafeChat.filter(message),0));
			CommandHandler.storeMessage(User, to, -1, message, "messenger");
		}else if(to == 0 && getSession().getHabbo().hasFuse("teamchat")){
			Varoke.getSessionManager().sendToUsersWithFuse("teamchat",new ConsoleChatMessageComposer(0, getSession().getHabbo().getUsername() + " : " + SafeChat.filter(message),0), User);
			CommandHandler.storeMessage(User, to,-1, message, "staffchat");
		}else if(getFriends().get(to).getSession() == null && SafeChat.isSafe(message) && to > 0){
			Varoke.getGame().getOfflineMessageManager().storeMessage(User, to, message);
			CommandHandler.storeMessage(User, to, -1, message, "offline");
		}
	}
	public void setInitialized(boolean b){
		initialized = b;
	}
	public boolean isInitialized(){ return initialized;}
	public void composeOfflineMessages() throws Exception{
		for(OfflineMessage om : Varoke.getGame().getOfflineMessageManager().getMessagesByUser(User)){
			getSession().sendComposer(new ConsoleChatMessageComposer(om.getFromId(), om.getMessage(),
					Varoke.getCurrentTimestamp() - om.getTime()
					));
			Varoke.getGame().getOfflineMessageManager().removeMessage(om);
		}
		
	}
	public void search(String query) throws Exception{
		List<Result> friends = new ArrayList<Result>();
		List<Result> others = new ArrayList<Result>();
		for(Result r : Varoke.getFactory().getMessengerFactory().search(query)){
			if(getFriends().containsKey(r))
				friends.add(r);
			else
				others.add(r);
		}
		getSession().sendComposer(new ConsoleSearchFriendMessageComposer(friends,others));
	}
}
