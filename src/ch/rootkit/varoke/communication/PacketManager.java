package ch.rootkit.varoke.communication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import ch.rootkit.varoke.communication.events.MessageEvent;
import ch.rootkit.varoke.communication.events.badges.GetUserBadgesMessageEvent;
import ch.rootkit.varoke.communication.events.badges.LoadBadgeInventoryMessageEvent;
import ch.rootkit.varoke.communication.events.badges.SetActivatedBadgesMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.CatalogueOfferConfigMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.GetCatalogIndexMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.GetCatalogPageMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.GetGiftWrappingConfigurationMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.PurchaseFromCatalogAsGiftMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.PurchaseFromCatalogMessageEvent;
import ch.rootkit.varoke.communication.events.catalog.RedeemVoucherMessageEvent;
import ch.rootkit.varoke.communication.events.handshake.CheckReleaseMessageEvent;
import ch.rootkit.varoke.communication.events.handshake.GenerateSecretKeyMessageEvent;
import ch.rootkit.varoke.communication.events.handshake.InitCryptoMessageEvent;
import ch.rootkit.varoke.communication.events.handshake.SSOTicketMessageEvent;
import ch.rootkit.varoke.communication.events.inventory.LoadItemsInventoryMessageEvent;
import ch.rootkit.varoke.communication.events.landing.GetHotelViewHallOfFameMessageEvent;
import ch.rootkit.varoke.communication.events.landing.GoToHotelViewMessageEvent;
import ch.rootkit.varoke.communication.events.landing.LandingLoadWidgetMessageEvent;
import ch.rootkit.varoke.communication.events.landing.LandingRefreshPromosMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.AcceptFriendMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.ConsoleInstantChatMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.ConsoleInviteFriendsMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.ConsoleSearchFriendsMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.DeclineFriendMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.DeleteFriendMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.FollowFriendMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.InitMessengerMessageEvent;
import ch.rootkit.varoke.communication.events.messenger.RequestFriendMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.AddFavouriteRoomMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.CanCreateRoomMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.CreateRoomMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.GoToRoomByNameMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.NavigatorGetFlatCategoriesMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.NewNavigatorAddSavedSearchEvent;
import ch.rootkit.varoke.communication.events.navigator.NewNavigatorDeleteSavedSearchEvent;
import ch.rootkit.varoke.communication.events.navigator.NewNavigatorMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.NewNavigatorResizeEvent;
import ch.rootkit.varoke.communication.events.navigator.RemoveFavouriteRoomMessageEvent;
import ch.rootkit.varoke.communication.events.navigator.SearchNewNavigatorEvent;
import ch.rootkit.varoke.communication.events.navigator.SetHomeRoomMessageEvent;
import ch.rootkit.varoke.communication.events.relationships.GetRelationshipsMessageEvent;
import ch.rootkit.varoke.communication.events.relationships.SetRelationshipMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.EnterPrivateRoomMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.RateRoomMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.RoomGetHeightmapMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.RoomGetInfoMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.RoomGetSettingsInfoMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.RoomOnLoadMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.SaveRoomThumbnailMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.FloorItemMoveMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.OpenGiftMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.PickUpItemMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.ReedemExchangeItemMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.RoomAddFloorItemMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.TriggerDiceCloseMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.TriggerDiceRollMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.TriggerItemMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.TriggerWallItemMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.items.WallItemMoveMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.users.RoomUserActionMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.users.TalkMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.users.ToggleSittingMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.users.UserDanceMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.users.UserSignMessageEvent;
import ch.rootkit.varoke.communication.events.rooms.users.UserWalkMessageEvent;
import ch.rootkit.varoke.communication.events.users.InfoRetrieveMessageEvent;
import ch.rootkit.varoke.communication.events.users.LoadUserProfileMessageEvent;
import ch.rootkit.varoke.communication.events.users.UserUpdateLookMessageEvent;
import ch.rootkit.varoke.communication.events.users.UserUpdateMottoMessageEvent;
import ch.rootkit.varoke.communication.events.wardrobe.WardrobeMessageEvent;
import ch.rootkit.varoke.communication.events.wardrobe.WardrobeUpdateMessageEvent;
import ch.rootkit.varoke.communication.headers.Incoming;

public class PacketManager {

	private HashMap<Short, MessageEvent> events;
	private List<Short> handshakeEvents;
	
	public PacketManager() {
		events = new HashMap<Short, MessageEvent>();
		handshakeEvents = new ArrayList<Short>();
	}
	
	public void bindHandshake(){
		events.put(Incoming.get("CheckReleaseMessageEvent"), new CheckReleaseMessageEvent());
		events.put(Incoming.get("InitCryptoMessageEvent"), new InitCryptoMessageEvent());
		events.put(Incoming.get("GenerateSecretKeyMessageEvent"), new GenerateSecretKeyMessageEvent());
		events.put(Incoming.get("SSOTicketMessageEvent"), new SSOTicketMessageEvent());
		for(Entry<Short, MessageEvent> e : events.entrySet()){
			handshakeEvents.add(e.getKey());
		}
	}
	
	public void bindUsers(){
		events.put(Incoming.get("InfoRetrieveMessageEvent"), new InfoRetrieveMessageEvent());
		events.put(Incoming.get("LoadUserProfileMessageEvent"), new LoadUserProfileMessageEvent());
		events.put(Incoming.get("UserUpdateLookMessageEvent"), new UserUpdateLookMessageEvent());
		events.put(Incoming.get("UserUpdateMottoMessageEvent"), new UserUpdateMottoMessageEvent());
	}
	
	public void bindLadingView(){
		events.put(Incoming.get("LandingLoadWidgetMessageEvent"), new LandingLoadWidgetMessageEvent());
		events.put(Incoming.get("GetHotelViewHallOfFameMessageEvent"), new GetHotelViewHallOfFameMessageEvent());
		events.put(Incoming.get("LandingRefreshPromosMessageEvent"), new LandingRefreshPromosMessageEvent());
		events.put(Incoming.get("GoToHotelViewMessageEvent"), new GoToHotelViewMessageEvent());
	}
	
	public void bindNavigator(){
		events.put(Incoming.get("NewNavigatorMessageEvent"), new NewNavigatorMessageEvent());
		events.put(Incoming.get("SearchNewNavigatorEvent"), new SearchNewNavigatorEvent());
		events.put(Incoming.get("NewNavigatorResizeEvent"), new NewNavigatorResizeEvent());
		events.put(Incoming.get("NewNavigatorAddSavedSearchEvent"), new NewNavigatorAddSavedSearchEvent());
		events.put(Incoming.get("NewNavigatorDeleteSavedSearchEvent"), new NewNavigatorDeleteSavedSearchEvent());
		events.put(Incoming.get("CanCreateRoomMessageEvent"), new CanCreateRoomMessageEvent());
		events.put(Incoming.get("NavigatorGetFlatCategoriesMessageEvent"), new NavigatorGetFlatCategoriesMessageEvent());
		events.put(Incoming.get("GoToRoomByNameMessageEvent"), new GoToRoomByNameMessageEvent());
		events.put(Incoming.get("CreateRoomMessageEvent"), new CreateRoomMessageEvent());
		events.put(Incoming.get("AddFavouriteRoomMessageEvent"), new AddFavouriteRoomMessageEvent());
		events.put(Incoming.get("RemoveFavouriteRoomMessageEvent"), new RemoveFavouriteRoomMessageEvent());
		events.put(Incoming.get("SetHomeRoomMessageEvent"), new SetHomeRoomMessageEvent());
	}
	
	public void bindRoom(){
		events.put(Incoming.get("RoomGetInfoMessageEvent"), new RoomGetInfoMessageEvent());
		events.put(Incoming.get("EnterPrivateRoomMessageEvent"), new EnterPrivateRoomMessageEvent());
		events.put(Incoming.get("RoomOnLoadMessageEvent"), new RoomOnLoadMessageEvent());
		events.put(Incoming.get("RoomGetHeightmapMessageEvent"), new RoomGetHeightmapMessageEvent());
		events.put(Incoming.get("RoomGetSettingsInfoMessageEvent"), new RoomGetSettingsInfoMessageEvent());
		events.put(Incoming.get("SaveRoomThumbnailMessageEvent"), new SaveRoomThumbnailMessageEvent());
		events.put(Incoming.get("RateRoomMessageEvent"), new RateRoomMessageEvent());
	}
	
	public void bindRoomUser(){
		events.put(Incoming.get("ShoutMessageEvent"), new TalkMessageEvent());
		events.put(Incoming.get("ChatMessageEvent"), new TalkMessageEvent());
		events.put(Incoming.get("UserWalkMessageEvent"), new UserWalkMessageEvent());
		events.put(Incoming.get("ToggleSittingMessageEvent"), new ToggleSittingMessageEvent());
		events.put(Incoming.get("RoomUserActionMessageEvent"), new RoomUserActionMessageEvent());
		events.put(Incoming.get("UserDanceMessageEvent"), new UserDanceMessageEvent());
		events.put(Incoming.get("UserSignMessageEvent"), new UserSignMessageEvent());
	}
	
	public void bindRoomItems(){
		events.put(Incoming.get("RoomAddFloorItemMessageEvent"), new RoomAddFloorItemMessageEvent());
		events.put(Incoming.get("FloorItemMoveMessageEvent"), new FloorItemMoveMessageEvent());
		events.put(Incoming.get("PickUpItemMessageEvent"), new PickUpItemMessageEvent());
		events.put(Incoming.get("OpenGiftMessageEvent"), new OpenGiftMessageEvent());
		events.put(Incoming.get("TriggerItemMessageEvent"), new TriggerItemMessageEvent());
		events.put(Incoming.get("WallItemMoveMessageEvent"), new WallItemMoveMessageEvent());
		events.put(Incoming.get("TriggerWallItemMessageEvent"), new TriggerWallItemMessageEvent());
		events.put(Incoming.get("ReedemExchangeItemMessageEvent"), new ReedemExchangeItemMessageEvent());
		events.put(Incoming.get("TriggerDiceRollMessageEvent"), new TriggerDiceRollMessageEvent());
		events.put(Incoming.get("TriggerDiceCloseMessageEvent"), new TriggerDiceCloseMessageEvent());
	}
	
	public void bindMessenger(){
		events.put(Incoming.get("InitMessengerMessageEvent"), new InitMessengerMessageEvent());
		events.put(Incoming.get("AcceptFriendMessageEvent"), new AcceptFriendMessageEvent());
		events.put(Incoming.get("DeclineFriendMessageEvent"), new DeclineFriendMessageEvent());
		events.put(Incoming.get("FollowFriendMessageEvent"), new FollowFriendMessageEvent());
		events.put(Incoming.get("ConsoleInstantChatMessageEvent"), new ConsoleInstantChatMessageEvent());
		events.put(Incoming.get("ConsoleInviteFriendsMessageEvent"), new ConsoleInviteFriendsMessageEvent());
		events.put(Incoming.get("DeleteFriendMessageEvent"), new DeleteFriendMessageEvent());
		events.put(Incoming.get("RequestFriendMessageEvent"), new RequestFriendMessageEvent());
		events.put(Incoming.get("ConsoleSearchFriendsMessageEvent"), new ConsoleSearchFriendsMessageEvent());
	}
	
	public void bindRelationships(){
		events.put(Incoming.get("SetRelationshipMessageEvent"), new SetRelationshipMessageEvent());
		events.put(Incoming.get("GetRelationshipsMessageEvent"), new GetRelationshipsMessageEvent());
	}
	
	public void bindBadgeInventory(){
		events.put(Incoming.get("LoadBadgeInventoryMessageEvent"), new LoadBadgeInventoryMessageEvent());
		events.put(Incoming.get("SetActivatedBadgesMessageEvent"), new SetActivatedBadgesMessageEvent());
		events.put(Incoming.get("GetUserBadgesMessageEvent"), new GetUserBadgesMessageEvent());
	}
	
	public void bindWardrobe(){
		events.put(Incoming.get("WardrobeMessageEvent"), new WardrobeMessageEvent());
		events.put(Incoming.get("WardrobeUpdateMessageEvent"), new WardrobeUpdateMessageEvent());
	}
	
	public void bindCatalog(){
		events.put(Incoming.get("GetCatalogIndexMessageEvent"), new GetCatalogIndexMessageEvent());
		events.put(Incoming.get("GetCatalogPageMessageEvent"), new GetCatalogPageMessageEvent());
		events.put(Incoming.get("PurchaseFromCatalogMessageEvent"), new PurchaseFromCatalogMessageEvent());
		events.put(Incoming.get("GetGiftWrappingConfigurationMessageEvent"), new GetGiftWrappingConfigurationMessageEvent());
		events.put(Incoming.get("PurchaseFromCatalogAsGiftMessageEvent"), new PurchaseFromCatalogAsGiftMessageEvent());
		events.put(Incoming.get("CatalogueOfferConfigMessageEvent"), new CatalogueOfferConfigMessageEvent());
//		Voucher
		events.put(Incoming.get("RedeemVoucherMessageEvent"), new RedeemVoucherMessageEvent());
	}
	
	public void bindInventory(){
		events.put(Incoming.get("LoadItemsInventoryMessageEvent"), new LoadItemsInventoryMessageEvent());
	}
	
	/**
	 * 
	 * @param id the incoming message id
	 * @return the message event
	 */
	public MessageEvent get(short id){
		return events.get(id);
	}
	
	/**
	 * 
	 * @param id the incoming message id
	 * @return if the message event is registered
	 */
	public boolean hasEvent(short id){
		return events.containsKey(id);
	}
	
	/**
	 * 
	 * @param id the incoming message id
	 * @return If the id is a handshake message
	 */
	public boolean isHandshake(short id){
		return handshakeEvents.contains(id);
	}
}
