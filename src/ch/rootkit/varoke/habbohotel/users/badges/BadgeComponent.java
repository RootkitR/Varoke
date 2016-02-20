package ch.rootkit.varoke.habbohotel.users.badges;

import java.util.HashMap;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.badges.LoadBadgesWidgetMessageComposer;
import ch.rootkit.varoke.communication.composers.badges.ReceiveBadgeMessageComposer;
import ch.rootkit.varoke.habbohotel.sessions.Session;
public class BadgeComponent {

	private int userId;
	private HashMap<String, Badge> badges;
	
	public BadgeComponent(int u, HashMap<String, Badge> b){
		userId = u;
		badges = b;
	}
	
	public Badge getBadge(String key){
		return badges.get(key);
	}
	
	public Session getSession(){ 
		return Varoke.getSessionManager().getSessionByUserId(userId);
	}
	
	public HashMap<String, Badge> getBadges(){
		return badges;
	}
	
	public int getUser() {
		return userId; 
	}
	
	public int size() {
		return badges.size(); 
	}
	
	public boolean hasBadge(String key){ 
		return badges.containsKey(key);
	}
	
	public void removeBadge(String key) throws Exception{
		badges.remove(key);
		Varoke.getFactory().getBadgeFactory().takeBadge(key, userId);
		getSession().sendComposer(new LoadBadgesWidgetMessageComposer(this));
	}
	
	public void addBadge(String key) throws Exception{
		if(!hasBadge(key)){
			badges.put(key, new Badge(key, 0));
			Varoke.getFactory().getBadgeFactory().giveBadge(key, userId);
			getSession().sendComposer(new ReceiveBadgeMessageComposer(key));
		}
	}
	
	public void resetSlots() throws Exception{
		for(Badge b : getBadges().values()){
			b.setSlot(0);
		}
		Varoke.getFactory().getBadgeFactory().resetSlots(getUser());
	}
}
