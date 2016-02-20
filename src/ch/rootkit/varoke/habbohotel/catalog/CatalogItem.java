package ch.rootkit.varoke.habbohotel.catalog;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.items.Item;

public class CatalogItem {

	private int Id;
	private int PageId;
	private int CostCredits;
	private int CostDiamonds;
	private int CostLoyalty;
	private int CostDuckets;
	private int Amount;
	private int Achievement;
	private int SongId;
	private int LimitedSells;
	private int LimitedStack;
	private int OrderNum;
	private int ItemId;
	private String ItemNames;
	private String SpecialName;
	private String ExtraData;
	private String Badge;
	
	private boolean OfferActive;
	private boolean ClubOnly;
	public CatalogItem(int id, int pageid, int itemId, String itemnames, String specialname, int costcredits, int costdiamonds,int costloyalty, int costduckets, int amount, int achievement, int songid,int limitedsells, int limitedstack, boolean offeractive, boolean clubonly, String extradata, String badge, int ordernum)
	{
		this.Id = id;
		this.PageId = pageid;
		this.ItemId = itemId;
		this.CostCredits = costcredits;
		this.CostDiamonds = costdiamonds;
		this.CostLoyalty = costloyalty;
		this.CostDuckets = costduckets;
		this.Amount = amount;
		this.Achievement = achievement;
		this.SongId = songid;
		this.LimitedSells = limitedsells;
		this.LimitedStack = limitedstack;
		this.OrderNum = ordernum;
		this.ItemNames = itemnames;
		this.SpecialName = specialname;
		this.ExtraData = extradata;
		this.Badge = badge;
		this.OfferActive = offeractive;
		this.ClubOnly = clubonly;
	}
	
	public int getId(){ 
		return this.Id;
	}
	
	public int getPageId(){ 
		return this.PageId;
	}
	
	public int getItemId(){ 
		return this.ItemId;
	}
	
	public int getCostCredits(){
		return this.CostCredits;
	}
	
	public int getCostDiamonds(){ 
		return this.CostDiamonds;
	}
	
	public int getCostLoyalty(){ 
		return this.CostLoyalty;
	}
	
	public int getCostDuckets(){ 
		return this.CostDuckets;
	}
	
	public int getAmount(){ 
		return this.Amount;
	}
	
	public int getAchievement(){ 
		return this.Achievement;
	}
	
	public int getSongId(){ 
		return this.SongId;
	}
	
	public int getLimitedSells(){ 
		return this.LimitedSells;
	}
	
	public int getLimitedStack(){ 
		return this.LimitedStack;
	}
	
	public int getOrderNum(){ 
		return this.OrderNum;
	}
	
	public String getItemNames(){ 
		return this.ItemNames;
	}
	
	public String getSpecialName(){ 
		return this.SpecialName;
	}
	
	public String getExtraData(){ 
		return this.ExtraData;
	}
	
	public String getBadge(){ 
		return this.Badge;
	}
	
	public boolean isOfferActive(){ 
		return this.OfferActive;
	}
	
	public boolean clubOnly(){ 
		return this.ClubOnly;
	}
	
	public boolean isLimited(){ 
		return this.LimitedStack > 0;
	}
	
	public Item getBaseItem(){ 
		return Varoke.getGame().getItemManager().getItem(getItemId());
	}
	
	public void compose(ServerMessage message) throws Exception
	{
		message.writeInt(getId());
		message.writeString(getItemNames());
		message.writeBoolean(false); // Rent
		message.writeInt(getCostCredits());
		if(getCostDiamonds() > 0){
			message.writeInt(getCostDiamonds());
			message.writeInt(105);
		}else{
			message.writeInt(getCostDuckets());
			message.writeInt(0);
		}
		message.writeBoolean(true); // GIFT
		message.writeInt(this.getBadge() == "" ? 1 : 2);
		if(this.getBadge() != null){
			message.writeString("b");
			message.writeString(this.getBadge());
		}
		message.writeString(getBaseItem().getType());
		message.writeInt(getBaseItem().getSpriteId());
		message.writeString(getExtraData()); // 20
		message.writeInt(getAmount());
		message.writeBoolean(isLimited());
		if(isLimited()){
			message.writeInt(getLimitedStack());
			message.writeInt(getLimitedSells());
		}
		message.writeInt(clubOnly() ? 1 : 0); // 0=normal ; 1=HC but can BUY; 2=Can't buy
		message.writeBoolean(isOfferActive() && !isLimited() && !getItemNames().startsWith("bot_"));
	}
	
	public void setLimitedSells(int i) {
		this.LimitedSells = i;
	}
}
