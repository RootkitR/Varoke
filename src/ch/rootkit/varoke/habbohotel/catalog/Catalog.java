package ch.rootkit.varoke.habbohotel.catalog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.composers.catalog.CatalogLimitedItemSoldOutMessageComposer;
import ch.rootkit.varoke.communication.composers.catalog.PurchaseOKMessageComposer;
import ch.rootkit.varoke.habbohotel.exceptions.GiftError;
import ch.rootkit.varoke.habbohotel.exceptions.PurchaseError;
import ch.rootkit.varoke.habbohotel.items.Item;
import ch.rootkit.varoke.habbohotel.sessions.Session;
import ch.rootkit.varoke.habbohotel.users.inventory.InventoryItem;
import ch.rootkit.varoke.utils.Logger;

public class Catalog {
	private HashMap<Integer, CatalogPage> catalogPages;
	private HashMap<Integer, CatalogItem> catalogItems;
	private List<Integer> oldWrapping;
	private List<Integer> newWrapping;
	public Catalog(){
		this.catalogPages = new HashMap<Integer, CatalogPage>();
		this.catalogItems = new HashMap<Integer, CatalogItem>();
		this.oldWrapping = new ArrayList<Integer>();
		this.newWrapping = new ArrayList<Integer>();
	}
	public void initialize() throws Exception{
		final long started = new Date().getTime();
		Logger.printVaroke("Initializing Catalog ");
		this.catalogPages = Varoke.getFactory().getCatalogFactory().readPages();
		for(CatalogPage page : catalogPages.values()){
			for(CatalogItem item : page.getItems()){
				this.catalogItems.put(item.getId(), item);
			}
		}
		for(Item item : Varoke.getGame().getItemManager().getItems()){
			if(item.getItemName().startsWith("present_gen"))
				this.oldWrapping.add(item.getSpriteId());
			else if(item.getItemName().startsWith("present_wrap"))
				this.newWrapping.add(item.getSpriteId());
		}
		Logger.printLine("(" +  (new Date().getTime() - started) + " ms)");
	}
	public List<CatalogPage> getPagesByRankAndParent(int rank, int parentId)
	{
		List<CatalogPage> result = new ArrayList<CatalogPage>();
		for(CatalogPage p : this.catalogPages.values())
		{
			if(rank >= p.getMinRank() && p.getParentId() == parentId)
			result.add(p);
		}
		return result;
	}
	public CatalogPage getPage(int id)
	{
		return this.catalogPages.get(id);
	}
	public List<Integer> getOldWrapping(){ return oldWrapping; }
	public List<Integer> getNewWrapping(){ return newWrapping; }
	public void purchase(int pageId, int itemId, String extraData, int amount, Session session) throws PurchaseError, Exception{
		CatalogPage page = catalogPages.get(pageId);
		if((!catalogPages.containsKey(pageId)) || page == null || (!page.isEnabled()) || (!page.hasItem(itemId)) || 
				(amount < 0 || amount > 100) || page.getMinRank() > session.getHabbo().getRank()) //TODO check for club, vip!!
			throw new PurchaseError(0);
		CatalogItem item = this.catalogItems.get(itemId);
		if(item == null  || (!item.isOfferActive() && amount > 1) || (item.isLimited() && item.getLimitedSells() >= item.getLimitedStack())
				|| item.getCostCredits() > session.getHabbo().getCredits() || item.getCostDiamonds() > session.getHabbo().getDiamonds()
				|| item.getCostDuckets() > session.getHabbo().getDuckets())
			throw new PurchaseError(0);
		int priceAmount = amount;
		int itemAmount = amount * item.getAmount();
		if (amount >= 6) 
			priceAmount -= (int)Math.ceil((double) amount / 6) * 2 - 1;
		if(session.getHabbo().getCredits() < (priceAmount * item.getCostCredits()) ||
				session.getHabbo().getDuckets() < (priceAmount * item.getCostDuckets()) ||
				session.getHabbo().getDiamonds() < (priceAmount * item.getCostDiamonds()))
			throw new PurchaseError(0);
		if(item.isLimited() && item.getLimitedSells() >= item.getLimitedStack()){
			session.sendComposer(new CatalogLimitedItemSoldOutMessageComposer());
			return;
		}
		if(item.isLimited()){
			priceAmount = 1;
			itemAmount = item.getAmount();
			item.setLimitedSells(item.getLimitedSells() + 1);
		}
		session.getHabbo().giveCredits(0 - (priceAmount * item.getCostCredits()));
		session.getHabbo().giveDiamonds(0 - (priceAmount * item.getCostDiamonds()));
		session.getHabbo().giveDuckets(0 - (priceAmount * item.getCostDuckets()));
		session.sendComposer(new PurchaseOKMessageComposer(session, item));
		for(int i = 0; i < itemAmount; i++){
			@SuppressWarnings("unused")
			InventoryItem invItem = session.getHabbo().getInventory().createItem(session.getHabbo().getId(),
					item.getBaseItem().getId(), extraData, item.getLimitedSells(), 0, false);//TODO group, builders club
		}
		if(item.getBadge() != "")
			session.getHabbo().getBadgeComponent().addBadge(item.getBadge());
	}
	public void purchaseAsGift(Session session, int pageId, int itemId, String extraData, String giftUser, String giftMessage, int giftSprite, int giftRibbon,int giftColor, boolean showUser) throws GiftError, PurchaseError, Exception{
		CatalogPage page = catalogPages.get(pageId);
		if((!catalogPages.containsKey(pageId)) || page == null || (!page.isEnabled()) || (!page.hasItem(itemId)) || page.getMinRank() > session.getHabbo().getRank()) //TODO check for club, vip!!
			throw new PurchaseError(0);
		CatalogItem item = this.catalogItems.get(itemId);
		Item giftItem = Varoke.getGame().getItemManager().getItemBySprite(giftSprite);
		if(item == null || (item.isLimited() && item.getLimitedSells() >= item.getLimitedStack())
				|| item.getCostCredits() > session.getHabbo().getCredits() || item.getCostDiamonds() > session.getHabbo().getDiamonds()
				|| item.getCostDuckets() > session.getHabbo().getDuckets() || !item.getBaseItem().allowGift() || giftItem == null)
			throw new PurchaseError(0);
		if(!Varoke.getFactory().getUserFactory().userExists(giftUser))
			throw new GiftError();
		session.getHabbo().giveCredits(0 - item.getCostCredits());
		session.getHabbo().giveDiamonds(0 - item.getCostDiamonds());
		session.getHabbo().giveDuckets(0 - item.getCostDuckets());
		session.sendComposer(new PurchaseOKMessageComposer(session, item));
		String params = session.getHabbo().getId() + ";" + giftMessage.replace(";","")+ ";" + giftRibbon + ";" + giftColor + ";" + showUser;
		if(Varoke.getSessionManager().getSessionByUsername(giftUser) == null){
			int userId = Varoke.getFactory().getUserFactory().getIdFromUser(giftUser);
			InventoryItem invItem = Varoke.getFactory().getInventoryFactory().createItem(userId, 
					giftItem.getId(), params, 0, 0, false);
			invItem.setExtraData(invItem.getExtraData() + ";" + invItem.getId());
			Varoke.getFactory().getInventoryFactory().storeGift(invItem.getId(), item.getBaseItem().getId(), extraData);
		}else{
			Session target = Varoke.getSessionManager().getSessionByUsername(giftUser);
			InventoryItem invItem = target.getHabbo().getInventory().createItem(target.getHabbo().getId(),
					giftItem.getId(), params, 0, 0, false);
			Varoke.getFactory().getInventoryFactory().storeGift(invItem.getId(), item.getBaseItem().getId(), extraData);
			target.sendNotif("Du hast ein Geschenk erhalten!", "notification_gift");
		}
	}
	public void dispose(){
		for(CatalogPage page : catalogPages.values()){
			page.getItems().clear();
		}
		this.catalogPages.clear();
		this.catalogItems.clear();
	}
	public CatalogItem getItemByBaseItem(int baseItemId) {
		for(CatalogItem item : catalogItems.values()){
			if(item.getBaseItem().getId() == baseItemId)
				return item;
		}
		return null;
	}
}
