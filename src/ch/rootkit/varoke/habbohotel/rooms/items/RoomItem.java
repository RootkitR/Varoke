package ch.rootkit.varoke.habbohotel.rooms.items;

import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.rooms.items.UpdateFurniStackMapMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.UpdateRoomItemMessageComposer;
import ch.rootkit.varoke.communication.composers.rooms.items.UpdateRoomWallItemMessageComposer;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.items.Item;
import ch.rootkit.varoke.habbohotel.pathfinding.AffectedTiles;
import ch.rootkit.varoke.habbohotel.pathfinding.Point;
import ch.rootkit.varoke.habbohotel.rooms.Room;
import ch.rootkit.varoke.habbohotel.rooms.items.interactions.Interactor;
import ch.rootkit.varoke.habbohotel.rooms.items.interactions.InteractorFactory;

public class RoomItem {

	private int Id;
	private int OwnerId;
	private int RoomId;
	private int BaseItem;
	private String ExtraData;
	private int X;
	private int Y;
	private double Z;
	private int Rotation;
	private String WallPosition;
	private int LimitedId;
	private int GroupId;
	private boolean BuildersFurni;
	private int giftTimer;
	public RoomItem(int id, int ownerId, int roomId, int baseItem, String extraData, int x, int y, double z, int rotation, String wallPosition, int limitedId, int groupId, boolean buildersFurni){
		Id = id;
		OwnerId = ownerId;
		RoomId = roomId;
		BaseItem = baseItem;
		ExtraData = extraData;
		X = x;
		Y = y;
		Z = z;
		Rotation = rotation;
		WallPosition = wallPosition;
		LimitedId = limitedId;
		BuildersFurni = buildersFurni;
		giftTimer = 0;
	}

	public int getId() { return Id; }
	public int getOwnerId() { return OwnerId; }
	public int getRoomId() { return RoomId; }
	public int getBaseItemId() { return BaseItem; }
	public String getExtraData() { return ExtraData; }
	public int getX() { return X; }
	public int getY() { return Y; }
	public double getZ() { return Z; }
	public int getRotation() { return Rotation; }
	public String getWallPosition() { return WallPosition; }
	public int getLimitedId() { return LimitedId; }
	public int getGroupId() { return GroupId; }
	public boolean isBuildersFurni() { return BuildersFurni; }
	public Room getRoom() { return Varoke.getGame().getRoomManager().getLoadedRoom(getRoomId()); }
	public Item getBaseItem() { return Varoke.getGame().getItemManager().getItem(getBaseItemId()); }
	public boolean isFloorItem() { return getBaseItem().getType().toLowerCase().equals("s"); }
	public double getHeight() { return getBaseItem().getDefaultStackHeight(); }
	public double getFullHeight() { return getHeight() + getZ(); }
	public int getGiftTimer() { return giftTimer; }
	public void setExtraData(String data){ ExtraData = data; }
	public void setX(int x){ X = x; }
	public void setY(int y){ Y = y; }
	public void setZ(double z){ Z = z; }
	public void setRotation(int rot){ Rotation = rot; }
	public void setWallPosition(String position){ WallPosition = position; }
	public void setBaseItem(int id){ BaseItem = id; }
	public void setGiftTimer(int timer){ giftTimer = timer; }
	public void compose(ServerMessage message) throws Exception {
		if(isFloorItem()){
			message.writeInt(getId());
			message.writeInt(getBaseItem().getSpriteId());
			message.writeInt(getX());
			message.writeInt(getY());
			message.writeInt(getRotation());
			message.writeString(getZ() + "");
			message.writeString(getHeight() + "");
			composeExtraData(message);
		}else{
			message.writeString(getId() + "");
			message.writeInt(getBaseItem().getSpriteId());
			message.writeString(getWallPosition());
			message.writeString(getExtraData());
		}
		message.writeInt(-1);
		message.writeInt(getBaseItem().getInteractionsModes() > 1 ? 1 : 0);
		message.writeInt(isBuildersFurni() ? -12345678 : getOwnerId());
	}
	public void composeExtraData(ServerMessage message) throws Exception {
		switch(getBaseItem().getInteractionType()){
			case "present":
			{
				String[] data = getExtraData().split(";");
				int giver = Integer.parseInt(data[0]);
				String msg = data[1];
				int ribbon = Integer.parseInt(data[2]);
				int color = Integer.parseInt(data[3]);
				boolean showUser = data[4].equals("true");
				Item giftItem = Varoke.getGame().getItemManager().getItem(
						Varoke.getFactory().getInventoryFactory().readGift(getId())
						);
				message.writeInt(ribbon * 1000 + color);
				message.writeInt(1);
				message.writeInt(showUser ? 6 : 4);
				message.writeString("EXTRA_PARAM");
				message.writeString("");
				message.writeString("MESSAGE");
				message.writeString(msg);
				if(showUser){
					message.writeString("PURCHASER_NAME");
					message.writeString(Varoke.getFactory().getUserFactory().getValueFromUser("username", giver).toString());
					message.writeString("PURCHASER_FIGURE");
					message.writeString(Varoke.getFactory().getUserFactory().getValueFromUser("look", giver).toString());
				}
				message.writeString("PRODUCT_CODE");
				message.writeString(giftItem.getItemName());
				message.writeString("state");
				message.writeString(data.length > 5 ? "1" : "0");
				break;
			}
			default:
			{
				if(getLimitedId() > 0){
					message.writeInt(1);
					message.writeInt(256);
					message.writeString(getExtraData());
					message.writeInt(getLimitedId());
					message.writeInt(Varoke.getGame().getCatalog().getItemByBaseItem(getBaseItemId()).getLimitedStack());
				}else{
					message.writeInt(1);
					message.writeInt(0);
					message.writeString(getExtraData());
				}
				return;
			}
		}
	}
	public void update() throws Exception {
		if(isFloorItem())
			getRoom().sendComposer(new UpdateRoomItemMessageComposer(this));
		else
			getRoom().sendComposer(new UpdateRoomWallItemMessageComposer(this));
		Varoke.getFactory().getItemFactory().saveItemState(this);
	}
	public List<Point> getAffectedTiles() {
		return AffectedTiles.getAffectedTiles(getBaseItem().getX(), getBaseItem().getY(), getX(), getY(), getRotation());
	}
	public void move(int x, int y, int rotation) throws Exception{
		boolean onlyRotation = x == getX() && y == getY();
		if(!getRoom().getItemManager().canPlace(x, y) && !onlyRotation){
			getRoom().sendComposer(new UpdateRoomItemMessageComposer(this));
			return;
		}
		if(!onlyRotation)
			setZ(getRoom().getItemManager().getHeight(x, y));
		int oldX = getX();
		int oldY = getY();
		setX(x);
		setY(y);
		setRotation(rotation);
		Varoke.getFactory().getItemFactory().saveItemState(this);
		getRoom().sendComposer(new UpdateRoomItemMessageComposer(this));
		getRoom().getGameMap().setWalkable(oldX, oldY, getRoom().getItemManager().canWalk(oldX, oldY));
		getRoom().getGameMap().setWalkable(x, y, getRoom().getItemManager().canWalk(x,y));
		getRoom().sendComposer(new UpdateFurniStackMapMessageComposer(getAffectedTiles(), getRoom()));
	}

	public Interactor getInteractor() {
		return InteractorFactory.getInteractor(this);
	}
}
