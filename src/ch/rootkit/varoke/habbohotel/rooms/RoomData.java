package ch.rootkit.varoke.habbohotel.rooms;

import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;

public class RoomData {
	
	private String Name;
	private String Description;
	/*private String Owner; gonna do this with magic.. :D */
	private String Wallpaper;
	private String Floor;
	private String Landscape;
	
	private int Id;
	private int State;
	private int UsersMax;
	private List<Integer> Ratings;
	private int Category;
	private int OwnerId;
	private Boolean AllowPets;
	private Boolean AllowPetsEat;
	private Boolean RoomMuted;
	private Boolean AllowWalkthrough;
	private String Model;
	private List<String> Tags;
	private String Image;
	private int wallHeight;
	
	public RoomData(int id, String name, String description, String roomModel,String image, int owner, int category, int usersMax, int state, String wallpaper, String floor, String landscape,
			boolean allowPets, boolean allowPetsEat, boolean wholeRoomMuted, List<String> tags, List<Integer> ratings, int wall_height, boolean walkthrough){
		Id = id;
		Name = name;
		Description = description;
		Model = roomModel;
		Image = image;
		OwnerId = owner;
		Category = category;
		UsersMax = usersMax;
		State = state;
		Wallpaper = wallpaper;
		Floor = floor;
		Landscape = landscape;
		AllowPets = allowPets;
		AllowPetsEat = allowPetsEat;
		RoomMuted = wholeRoomMuted;
		Tags = tags;
		Ratings = ratings;
		wallHeight = wall_height;
		AllowWalkthrough = walkthrough;
	}
	
	public int getId(){ 
		return Id;
	}
	
	public int getState(){ 
		return State;
	}
	
	public int getUsersMax(){ 
		return UsersMax;
	}
	
	public int getScore(){
		return Ratings.size();
	}
	
	public int getCategory(){ 
		return Category;
	}
	
	public int getOwner(){ 
		return OwnerId;
	}
	
	public String getName(){ 
		return Name;
	}
	
	public String getDescription(){
		return Description;
	}
	
	public String getImage(){
		return Image;
	}
	
	public int getWallHeight(){
		return wallHeight;
	}
	
	public int getUsersOnline(){
		return Varoke.getGame().getRoomManager().getUsersInRoom(getId());
	}
	
	public String getOwnerName(){ 
		try {
			return (String) Varoke.getFactory().getUserFactory().getValueFromUser("username", getOwner());
		} catch (Exception e) {
			return "";}
	}
	
	public String getModelName(){ 
		return Model;
	}
	
	public String getWallpaper(){
		return Wallpaper;
	}
	
	public String getFloor(){
		return Floor;
	}
	
	public String getLandscape(){
		return Landscape;
	}
	
	public Boolean petsAllowed(){ 
		return AllowPets;
	}
	
	public Boolean petsCanEat(){ 
		return AllowPetsEat;
	}
	
	public Boolean roomMuted(){ 
		return RoomMuted;
	}
	
	public Boolean allowWalkthrough(){ 
		return AllowWalkthrough; 
	}
	
	public Boolean hasTag(String tag){
		return getTags().contains(tag);
	}
	
	public Boolean canRate(int userId){ 
		return Ratings.contains(userId);
	}
	
	public List<String> getTags(){ 
		return Tags;
	}
	
	public void serialize(ServerMessage message)throws Exception{
		serialize(message, false);
	}
	
	public void serialize(ServerMessage message, boolean showEvent) throws Exception{
		message.writeInt(getId());
		message.writeString(getName());
		message.writeInt(getOwner());
		message.writeString(getOwnerName());
		message.writeInt(getState()); //state
		message.writeInt(Varoke.getGame().getRoomManager().getUsersInRoom(getId())); //users now
		message.writeInt(75);//usersmax
		message.writeString(getDescription());
		message.writeInt(0); // trade state
		message.writeInt(getScore()); //likes
		message.writeInt(0); //ranking
		message.writeInt(getCategory()); //category
		message.writeInt(getTags().size()); //Tags count
		for(String tag : getTags())
			message.writeString(tag);
		int enumType = 0;
		if (petsAllowed()) enumType += 16;
		if(showEvent) enumType += 4;
		if(getImage().length() > 0) enumType += 1;
        message.writeInt(enumType + 8);
        if(getImage().length() > 0)
        	message.writeString(getImage());
        if(showEvent){
        	message.writeString(getName());
        	message.writeString(getDescription());
        	message.writeInt(0);
        }
	}
	
	public void addRating(int userId) throws Exception{
		this.Ratings.add(userId);
		Varoke.getFactory().getRoomFactory().addRating(getId(), userId);
	}
}
