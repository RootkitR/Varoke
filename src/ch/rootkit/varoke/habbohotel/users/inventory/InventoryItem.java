package ch.rootkit.varoke.habbohotel.users.inventory;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.items.Item;

public class InventoryItem {

	private int Id;
	private int BaseId;
	private int UserId;
	private int GroupId;
	private int LimitedId;
	private String ExtraData;
	private boolean BuildersItem;
	public InventoryItem(int id,int userId, int baseId, int groupId, int limitedId, String extraData, boolean isBuildersItem){
		Id = id;
		BaseId = baseId;
		UserId = userId;
		GroupId = groupId;
		LimitedId = limitedId;
		ExtraData = extraData;
		BuildersItem = isBuildersItem;
	}
	public int getId() { return Id;}
	public int getBaseId() { return BaseId; }
	public int getUserId() { return UserId; }
	public int getGroupId() { return GroupId; }
	public int getLimitedId() { return LimitedId; }
	public String getExtraData() { return ExtraData; }
	public boolean isBuildersClubItem() { return BuildersItem; }
	public Item getBaseItem() { return Varoke.getGame().getItemManager().getItem(getBaseId()); }
	public void compose(ServerMessage message) throws Exception {
		message.writeInt(getId());
		message.writeString(getBaseItem().getType().toUpperCase());
		message.writeInt(getId());
		message.writeInt(getBaseItem().getSpriteId());
		int presentParam = 0;
		if(getBaseItem().getInteractionType().equals("present")){
			int ribbon = Integer.parseInt(getExtraData().split(";")[2]);
			int color = Integer.parseInt(getExtraData().split(";")[3]);
			presentParam = (ribbon * 1000 + color);
		}
		if (getBaseItem().getItemName().contains("a2") || getBaseItem().getItemName().equals("floor"))
			message.writeInt(3);
        else if (getBaseItem().getItemName().contains("wallpaper") && !getBaseItem().getItemName().equals("wildwest_wallpaper"))
        	message.writeInt(2);
        else if (getBaseItem().getItemName().contains("landscape"))
        	message.writeInt(4);
        else if(getBaseItem().getType().equals("s"))
        	message.writeInt(0);
        else
        	message.writeInt(1);
        message.writeInt(0); // null extraparam
        message.writeString("");// ExtraData!!
        //extraparams for special furnis.. :)
        message.writeBoolean(getBaseItem().allowRecycle());
        message.writeBoolean(getBaseItem().allowTrade());
        message.writeBoolean(getBaseItem().allowInventoryStack());
        message.writeBoolean(false); //SELLABLE_ICON
        message.writeInt(-1); //secondsToExpiration
        message.writeBoolean(true); //hasRentPeriodStarted
        message.writeInt(-1);
        if(!getBaseItem().getType().toLowerCase().equals("s"))
        	return;
        message.writeString(""); //slotId
        message.writeInt(presentParam);
	}
	public void setExtraData(String data) throws Exception {
		this.ExtraData = data;
		Varoke.getFactory().getInventoryFactory().updateExtraData(this);
	}
}
