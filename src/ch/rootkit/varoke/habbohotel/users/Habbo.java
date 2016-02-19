package ch.rootkit.varoke.habbohotel.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.navigator.FavouriteRoomsUpdateMessageComposer;
import ch.rootkit.varoke.communication.composers.users.ActivityPointsNotificationMessageComposer;
import ch.rootkit.varoke.communication.composers.users.CreditsBalanceMessageComposer;
import ch.rootkit.varoke.habbohotel.messenger.Friend;
import ch.rootkit.varoke.habbohotel.messenger.Messenger;
import ch.rootkit.varoke.habbohotel.navigator.NavigatorSearch;
import ch.rootkit.varoke.habbohotel.relationships.Relationship;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.badges.BadgeComponent;
import ch.rootkit.varoke.habbohotel.users.inventory.Inventory;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;
import ch.rootkit.varoke.habbohotel.users.wardrobe.Wardrobe;

public class Habbo {
	private int Id;
	private String Username;
	private String Mail;
	private int Rank;
	private String Look;
	private String Gender;
	private String Motto;
	private int Credits;
	private int Duckets;
	private int Diamonds;
	private long LastLogin;
	private long AccountCreated;
	private int loadingRoom;
	private int currentRoomId = -1;
	private int homeRoom;
	private Preferences prefer;
	private List<NavigatorSearch> searches;
	private List<Integer> FavoriteRooms;
	private Messenger messenger;
	private boolean muted;
	private List<Relationship> relationships;
	private BadgeComponent badges;
	private Wardrobe wardrobe;
	private Inventory inventory;
	public Habbo(int id, String username, String mail, int rank, String look, String gender, String motto, int credits, int duckets, int diamonds, long created, Preferences p, List<NavigatorSearch> s, List<Integer> favs, int home, Messenger m, List<Relationship> relations, BadgeComponent b, Wardrobe w, HashMap<Integer, InventoryItem> items)
	{
		Id = id;
		Username = username;
		Mail = mail;
		Rank = rank;
		Look = look;
		Gender = (gender.equals("M") || gender.equals("F")) ? gender : "M";
		Motto = motto;
		Credits = credits;
		Duckets = duckets;
		Diamonds = diamonds;
		LastLogin = Varoke.getCurrentTimestamp();
		prefer = p;
		searches = s;
		FavoriteRooms = favs;
		homeRoom = home;
		messenger = m;
		AccountCreated = created;
		if(hasFuse("teamchat"))
			messenger.getFriends().put(0, new Friend(0 ,"Team Chat", Look,"Hello World!",false,false, Id));
		relationships = relations;
		badges = b;
		wardrobe = w;
		inventory = new Inventory(this, items);
	}
	public int getId(){ return Id;}
	public String getUsername() { return Username;}
	public String getMail(){ return Mail;}
	public int getRank(){ return Rank;}
	public String getLook(){ return Look;}
	public String getGender(){ return Gender;}
	public String getMotto(){ return Motto;}
	public int getCredits(){ return Credits;}
	public int getDuckets(){ return Duckets;}
	public int getDiamonds(){ return Diamonds;}
	public long getLastLogin(){ return LastLogin;}
	public int getLoadingRoom(){ return loadingRoom;}
	public List<NavigatorSearch> getSearches(){ return searches;}
	public List<Integer> getFavorites(){ return FavoriteRooms;}
	public int getHome(){ return homeRoom;}
	public Room getCurrentRoom()
	{
		try {return Varoke.getGame().getRoomManager().getRoom(currentRoomId);} catch (Exception e) {return null;}
	}
	public int getCurrentRoomId(){ return currentRoomId;}
	public Preferences getPreferences(){ return prefer;}
	public Session getSession(){ return Varoke.getSessionManager().getSessionByUserId(getId());}
	public Messenger getMessenger(){ return messenger;}
	public List<Relationship> getRelationships(){ return relationships;}
	public Wardrobe getWardrobe() { return wardrobe;}
	public boolean isMuted() { return muted;}
	public Relationship getRelationship(int userId){
		for(Relationship r : getRelationships()){
			if(r.getTo() == userId)
				return r;
		}
		return null;
	}
	public Date getAccountCreated(){
		return new Date(AccountCreated);
	}
	public BadgeComponent getBadgeComponent(){ return badges; }
	public Inventory getInventory() { return inventory; }
	
	public void setCurrentRoom(int id){ currentRoomId = id;}
	public void setLoadingRoom(int id){ loadingRoom = id;}
	public void setPreferences(Preferences pref){ prefer = pref;}
	public void setHome(int id){ homeRoom = id;}
	public void setLook(String look){ Look = look;}
	public void setGender(String gender){ Gender = gender;}
	public void setMotto(String motto){ Motto = motto;}
	public boolean hasFuse(String key){
		return Varoke.getGame().getPermissions().hasFuse(getRank(), key);
	}
	public void addSearch(String value, String search){
		try {
			this.searches.add(Varoke.getFactory().getNavigatorFactory().createUserSearch(getId(), value, search));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void removeSearch(int id){
		for(NavigatorSearch ns : new ArrayList<NavigatorSearch>(getSearches())){
			if(ns.getId() == id){
				getSearches().remove(ns);
				break;
			}
		}
		try {
			Varoke.getFactory().getNavigatorFactory().deleteUserSearch(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void addFavorite(int roomId) throws Exception{
		if(!this.FavoriteRooms.contains(roomId)){
			this.FavoriteRooms.add(roomId);
			Varoke.getFactory().getUserFactory().addFavorite(getId(), roomId);
			getSession().sendComposer(new FavouriteRoomsUpdateMessageComposer(roomId, true));
		}
	}
	public void removeFavorite(int roomId) throws Exception{
		if(this.FavoriteRooms.contains(roomId)){
			this.FavoriteRooms.remove(roomId);
			Varoke.getFactory().getUserFactory().removeFavorite(getId(), roomId);
			getSession().sendComposer(new FavouriteRoomsUpdateMessageComposer(roomId, false));
		}
	}
	public void removeRelationship(int userId) throws Exception{
		Relationship toDelete = getRelationship(userId);
		if(toDelete != null && this.getRelationships().contains(toDelete))
			this.getRelationships().remove(toDelete);
	}
	public void addRelationship(Relationship r) throws Exception{
		if(getRelationship(r.getTo()) == null)
			getRelationships().add(r);
	}
	public void mute(boolean muted) { this.muted = muted;}
	public void giveCredits(int credits) throws Exception{
		this.Credits += credits;
		Varoke.getFactory().getUserFactory().updateValue(getId(), "credits", this.Credits);
		getSession().sendComposer(new CreditsBalanceMessageComposer(this.Credits));
	}
	public void giveDuckets(int duckets) throws Exception{
		this.Duckets += duckets;
		Varoke.getFactory().getUserFactory().updateValue(getId(), "duckets", this.Duckets);
		getSession().sendComposer(new ActivityPointsNotificationMessageComposer(this.Duckets, duckets, 0));
	}
	public void giveDiamonds(int diamonds) throws Exception{
		this.Diamonds += diamonds;
		Varoke.getFactory().getUserFactory().updateValue(getId(), "diamonds", this.Diamonds);
		getSession().sendComposer(new ActivityPointsNotificationMessageComposer(this.Diamonds,diamonds, 5));
	}
}
