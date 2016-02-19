package ch.rootkit.varoke.habbohotel.catalog.pages;

import ch.rootkit.varoke.communication.messages.ServerMessage;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;

public class Page {
	private CatalogPage page;
	public Page(CatalogPage p){
		page = p;
	}
	public CatalogPage getPage(){ return page; }
	public void compose(ServerMessage message) throws Exception{
		
	}
}
