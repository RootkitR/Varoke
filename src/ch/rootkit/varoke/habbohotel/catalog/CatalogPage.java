package ch.rootkit.varoke.habbohotel.catalog;

import java.util.ArrayList;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.pages.Page;

public class CatalogPage {

	private int Id;
	private int ParentId;
	private int Icon;
	private int MinRank;
	private int OrderNum;
	
	private String CodeName;
	private String Caption;
	private String PageLayout;
	private String PageHeadline;
	private String PageTeaser;
	private String PageSpecial;
	private String PageText1;
	private String PageText2;
	private String PageTextDetails;
	private String PageTextTeaser;
	private String PageLinkDescription;
	private String PageLinkName;
	
	private boolean Visible;
	private boolean Enabled;
	private boolean ClubOnly;
	private boolean VipOnly;
	
	private List<CatalogItem> Items;
	private List<Integer> FlatOffers;
	private Page layout;
	
	public CatalogPage(int id, int parentid, String codename, String caption, int icon, boolean visible, boolean enabled, int minrank, boolean clubonly, int ordernum, String pagelayout,String pageheadline, String pageteaser, String pagespecial, String pagetext1, String pagetext2, String pagetextdetails,String pagetextteaser, boolean viponly, String pagelinkdesc, String pagelinkname, List<CatalogItem> items){
		this.Id = id;
		this.ParentId = parentid;
		this.Icon = icon;
		this.MinRank = minrank;
		this.OrderNum = ordernum;
		this.CodeName = codename;
		this.Caption = caption;
		this.PageLayout = pagelayout;
		this.PageHeadline = pageheadline;
		this.PageTeaser = pageteaser;
		this.PageSpecial = pagespecial;
		this.PageText1 = pagetext1;
		this.PageText2 = pagetext2;
		this.PageTextDetails = pagetextdetails;
		this.PageTextTeaser = pagetextteaser;
		this.PageLinkDescription = pagelinkdesc;
		this.PageLinkName = pagelinkname;
		this.Visible = visible;
		this.Enabled = enabled;
		this.ClubOnly = clubonly;
		this.VipOnly = viponly;
		this.Items = items;
		this.FlatOffers = new ArrayList<Integer>();
		for(CatalogItem i : Items)
		{
			try{
			if(i.getBaseItem().getFlatId() != -1)
				this.FlatOffers.add(i.getBaseItem().getFlatId());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		this.layout = Varoke.getFactory().getCatalogFactory().getPageLayout(this);
	}
	
	public int getId(){ 
		return this.Id;
	}
	
	public int getParentId(){ 
		return this.ParentId;
	}
	
	public int getIcon(){ 
		return this.Icon;
	}
	
	public int getMinRank(){ 
		return this.MinRank;
	}
	
	public int getOrderNum(){ 
		return this.OrderNum;
	}
	
	public String getCodeName(){
		return this.CodeName;
	}
	
	public String getCaption(){ 
		return this.Caption;
	}
	
	public String getLayout(){
		return this.PageLayout;
	}
	
	public String getHeadline(){
		return this.PageHeadline;
	}
	
	public String getTeaser(){ 
		return this.PageTeaser;
	}
	
	public String getPageSpecial(){
		return this.PageSpecial;
	}
	
	public String getText1(){ 
		return this.PageText1;
	}
	
	public String getText2(){ 
		return this.PageText2;
	}
	
	public String getTextDetails(){
		return this.PageTextDetails;
	}
	
	public String getTextTeaser(){ 
		return this.PageTextTeaser;
	}
	
	public String getLinkDescription(){
		return this.PageLinkDescription;
	}
	
	public String getLinkName(){
		return this.PageLinkName;
	}
	
	public boolean isVisible(){ 
		return this.Visible;
	}
	
	public boolean isEnabled(){ 
		return this.Enabled;
	}
	
	public boolean isClubOnly(){ 
		return this.ClubOnly;
	}
	
	public boolean isVipOnly(){ 
		return this.VipOnly;
	}
	
	public List<CatalogItem> getItems(){ 
		return this.Items;
	}
	
	public List<Integer> getFlatOffers(){ 
		return this.FlatOffers;
	}
	
	public Page getPage() { 
		return layout; 
	}
	
	public boolean hasItem(int itemId){
		for(CatalogItem item : getItems()){
			if(item != null && item.getId() == itemId)
				return true;
		}
		return false;
	}
	
	public void compose(ServerMessage message) throws Exception{
		this.getPage().compose(message);
	}
}
