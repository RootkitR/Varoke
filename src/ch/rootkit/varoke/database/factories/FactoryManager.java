package ch.rootkit.varoke.database.factories;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Connection;

public class FactoryManager {
	private UserFactory userFactory;
	private NavigatorFactory navigatorFactory;
	private RoomFactory roomFactory;
	private MessengerFactory messengerFactory;
	private VarokeFactory varokeFactory;
	private RelationshipFactory relationshipFactory;
	private BadgeFactory badgeFactory;
	private WardrobeFactory wardrobeFactory;
	private CatalogFactory catalogFactory;
	private ItemFactory itemFactory;
	private InventoryFactory inventoryFactory;
	
	public FactoryManager(){
		userFactory = new UserFactory();
		navigatorFactory = new NavigatorFactory();
		roomFactory = new RoomFactory();
		messengerFactory = new MessengerFactory();
		varokeFactory = new VarokeFactory();
		relationshipFactory = new RelationshipFactory();
		badgeFactory = new BadgeFactory();
		wardrobeFactory = new WardrobeFactory();
		catalogFactory = new CatalogFactory();
		itemFactory = new ItemFactory();
		inventoryFactory = new InventoryFactory();
	}
	
	public UserFactory getUserFactory(){
		return userFactory;
	}
	
	public NavigatorFactory getNavigatorFactory(){
		return navigatorFactory;
	}
	
	public RoomFactory getRoomFactory(){
		return roomFactory;
	}
	
	public MessengerFactory getMessengerFactory(){
		return messengerFactory;
	}
	
	public VarokeFactory getVarokeFactory(){
		return varokeFactory;
	}
	
	public RelationshipFactory getRelationshipFactory(){
		return relationshipFactory;
	}
	
	public BadgeFactory getBadgeFactory(){
		return badgeFactory;
	}
	
	public WardrobeFactory getWardrobeFactory(){
		return wardrobeFactory;
	}
	
	public CatalogFactory getCatalogFactory(){
		return catalogFactory;
	}
	
	public ItemFactory getItemFactory(){
		return itemFactory;
	}
	
	public InventoryFactory getInventoryFactory(){
		return inventoryFactory;
	}
	
	public void dispose(Connection cn, PreparedStatement ps, ResultSet rs) throws Exception{
		if(cn != null)
			cn.close();
		if(ps != null)
			ps.close();
		if(rs != null)
			rs.close();
		cn = null;
		ps = null;
		rs = null;
	}
}
