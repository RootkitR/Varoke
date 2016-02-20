package ch.rootkit.varoke.habbohotel.items;
import java.util.List;
import java.util.ArrayList;

public class Item {
	private int Id;
	private int Sprite;
	private int FlatId;
	private int InteractionModeCount;
	private int EffectId;
	private int X;
	private int Y;
	private String ItemName;
	private String PublicName;
	private String Type;
	private String InteractionType;
	private List<Integer> VendingIds;
	private List<Double> StackHeight;
	
	private boolean CanStack;
	private boolean CanSit;
	private boolean CanWalk;
	private boolean AllowRecycle;
	private boolean AllowTrade;
	private boolean AllowMarketplaceSell;
	private boolean AllowGift;
	private boolean AllowInventoryStack;
	private boolean Subscriber;
	
	public Item(int id, int sprite, int flatId, String itemName, String publicName, String type, String stackHeight, boolean canStack, boolean allowRecycle, boolean allowTrade, boolean allowMarketplaceSell, boolean allowGift, boolean allowInventoryStack, String interactionType, int interactionModeCount, String vendingId, boolean subscriber, int effectId, int x, int y, boolean cansit, boolean canwalk){
		try{
		this.Id = id;
		this.Sprite = sprite;
		this.FlatId = flatId;
		this.InteractionModeCount = interactionModeCount;
		this.EffectId = effectId;
		this.ItemName = itemName;
		this.PublicName = publicName;
		this.Type = type;
		this.InteractionType = interactionType;
		this.VendingIds = new ArrayList<Integer>();
		for(String s : vendingId.split(",",999))
		{this.VendingIds.add(Integer.parseInt(s));}
		this.StackHeight = new ArrayList<Double>();
		for(String s : stackHeight.split(";",999))
		{this.StackHeight.add(Double.parseDouble(s));}
		this.CanStack = canStack;
		this.AllowRecycle = allowRecycle;
		this.AllowTrade = allowTrade;
		this.AllowMarketplaceSell = allowMarketplaceSell;
		this.AllowGift = allowGift;
		this.AllowInventoryStack = allowInventoryStack;
		this.Subscriber = subscriber;
		this.X = x;
		this.Y = y;
		this.CanSit = cansit;
		this.CanWalk = canwalk;
		}catch(Exception ex){
			System.out.println("Item ID: " + this.Id + "\n");
			ex.printStackTrace();
			//System.out.println("Error while loading Item Id: " + this.Id + "\n" + ex.getMessage());
		}
	}
	
	public int getId(){
		return this.Id;
	}
	
	public int getFlatId(){
		return this.FlatId;
	}
	
	public int getInteractionsModes(){
		return this.InteractionModeCount;
	}
	
	public int getEffect(){ 
		return this.EffectId;
	}
	
	public int getSpriteId(){ 
		return Sprite;
	}
	
	public int getX(){ 
		return X;
	}
	
	public int getY(){
		return Y;
	}
	
	public String getPublicName(){
		return PublicName;
	}
	
	public String getItemName(){
		return this.ItemName;
	}
	
	public String getType(){
		return this.Type;
	}
	
	public String getInteractionType(){
		return this.InteractionType;
	}
	
	public List<Integer> getVendingIds(){
		return this.VendingIds;
	}
	
	public List<Double> getStackHeight(){
		return this.StackHeight;
	}
	
	public Double getDefaultStackHeight(){
		return this.StackHeight.get(0);
	}
	
	public boolean canStack(){
		return this.CanStack;
	}
	
	public boolean allowRecycle(){
		return this.AllowRecycle;
	}
	
	public boolean allowTrade(){
		return this.AllowTrade;
	}
	
	public boolean allowMarketplaceSell(){
		return this.AllowMarketplaceSell;
	}
	
	public boolean allowGift(){
		return this.AllowGift;
	}
	
	public boolean allowInventoryStack(){
		return this.AllowInventoryStack;
	}
	
	public boolean isSubscriber(){
		return this.Subscriber;
	}
	
	public boolean canSit(){ 
		return CanSit;
	}
	
	public boolean canWalk(){
		return CanWalk;
	}
}