package ch.rootkit.varoke.habbohotel;

import java.util.Date;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.PacketManager;
import ch.rootkit.varoke.communication.headers.Incoming;
import ch.rootkit.varoke.communication.headers.Outgoing;
import ch.rootkit.varoke.habbohotel.catalog.Catalog;
import ch.rootkit.varoke.habbohotel.chat.SafeChat;
import ch.rootkit.varoke.habbohotel.items.ItemManager;
import ch.rootkit.varoke.habbohotel.landingview.LandingView;
import ch.rootkit.varoke.habbohotel.messenger.offline.OfflineMessageManager;
import ch.rootkit.varoke.habbohotel.navigator.Navigator;
import ch.rootkit.varoke.habbohotel.rooms.RoomManager;
import ch.rootkit.varoke.habbohotel.threading.ThreadPool;
import ch.rootkit.varoke.habbohotel.users.Permissions;
import ch.rootkit.varoke.utils.Logger;

public class Game {

	private PacketManager packetManager;
	private Navigator navigator;
	private RoomManager roomManager;
	private Permissions permissions;
	private OfflineMessageManager offlineMessageManager;
	private Catalog catalog;
	private ItemManager itemManager;
	private ThreadPool threadPool;
	private LandingView landingView;
	
	public Game() throws Exception {
		packetManager = new PacketManager();
		navigator = new Navigator();
		roomManager = new RoomManager();
		permissions = new Permissions();
		offlineMessageManager = new OfflineMessageManager();
		catalog = new Catalog();
		itemManager = new ItemManager();
		threadPool = new ThreadPool();
		landingView = new LandingView();
		// TODO Add things like Things Manager etc.. XD
	}
	
	public void initialize() throws Exception{
		itemManager.initialize();
		catalog.initialize();
		navigator.initialize();
		roomManager.initialize();
		landingView.initialize();
		SafeChat.init();
		initPacketManager();
	}
	
	public void initPacketManager(){
		final long started = new Date().getTime();
		Logger.printVaroke("Register Packets ");
		Incoming.init(Varoke.Version);
		Outgoing.init(Varoke.Version);
		packetManager.bindHandshake();
		packetManager.bindUsers();
		packetManager.bindLadingView();
		packetManager.bindNavigator();
		packetManager.bindRoom();
		packetManager.bindRoomItems();
		packetManager.bindRoomUser();
		packetManager.bindMessenger();
		packetManager.bindRelationships();
		packetManager.bindBadgeInventory();
		packetManager.bindWardrobe();
		packetManager.bindCatalog();
		packetManager.bindInventory();
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	
	public PacketManager getPacketManager(){
		return packetManager;
	}
	
	public Navigator getNavigator(){
		return navigator;
	}
	
	public RoomManager getRoomManager(){
		return roomManager;
	}
	
	public Permissions getPermissions(){
		return permissions;
	}
	
	public OfflineMessageManager getOfflineMessageManager(){
		return offlineMessageManager;
	}
	
	public Catalog getCatalog(){
		return catalog;
	}
	
	public ItemManager getItemManager(){
		return itemManager;
	}
	
	public ThreadPool getThreadPool(){
		return threadPool;
	}
	
	public LandingView getLanding(){
		return landingView;
	}
	
	public void refreshCatalog() throws Exception{
		getItemManager().dispose();
		itemManager = new ItemManager();
		itemManager.initialize();
		getCatalog().dispose();
		catalog = new Catalog();
		catalog.initialize();
	}
	
}
