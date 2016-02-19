package ch.rootkit.varoke.database.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.catalog.CatalogItem;
import ch.rootkit.varoke.habbohotel.catalog.CatalogPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.BadgeDisplaysPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.BotsPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.BuildersClubAddOnsPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.BuildersClubFrontPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.BuildersColorPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.ClubGiftsPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.DefaultBuildersClubPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.DefaultPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.DucketsInfoPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.FrontPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.GuildCustomPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.GuildForumPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.GuildFrontPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.LimitedSoldPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.NormalBuildersClubAddOnsPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.NormalBuildersClubFrontpage;
import ch.rootkit.varoke.habbohotel.catalog.pages.Page;
import ch.rootkit.varoke.habbohotel.catalog.pages.PetsPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.RecyclerInfoPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.RecyclerPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.RecyclerPrizesPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.RoomAdsPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.SoundmachinePage;
import ch.rootkit.varoke.habbohotel.catalog.pages.SpacesPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.ThrophiesPage;
import ch.rootkit.varoke.habbohotel.catalog.pages.VIPPage;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class CatalogFactory {
	public CatalogFactory(){
	}
	public HashMap<Integer, CatalogPage> readPages() throws Exception{
		HashMap<Integer, CatalogPage> result = new HashMap<Integer, CatalogPage>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * from catalog_pages ORDER BY order_num ASC");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			result.put(rs.getInt("id"), new CatalogPage(
					rs.getInt("id"),
					rs.getInt("parent_id"),
					rs.getString("code_name"),
					rs.getString("caption"),
					rs.getInt("icon_image"),
					Varoke.stringToBool(rs.getString("visible")),
					Varoke.stringToBool(rs.getString("enabled")),
					rs.getInt("min_rank"),
					Varoke.stringToBool(rs.getString("club_only")),
					rs.getInt("order_num"),
					rs.getString("page_layout"),
					rs.getString("page_headline"),
					rs.getString("page_teaser"),
					rs.getString("page_special"),
					rs.getString("page_text1"),
					rs.getString("page_text2"),
					rs.getString("page_text_details"),
					rs.getString("page_text_teaser"),
					Varoke.stringToBool(rs.getString("vip_only")),
					rs.getString("page_link_description"),
					rs.getString("page_link_pagename"),
					readItems(rs.getInt("id"))
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public List<CatalogItem> readItems(int pageId) throws Exception{
		List<CatalogItem> result = new ArrayList<CatalogItem>();
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM catalog_items WHERE page_id=? ORDER BY order_num ASC");
		ps.setInt(1, pageId);
		ResultSet rs = ps.executeQuery();
		while(rs.next()){
			result.add(new CatalogItem(
					rs.getInt("id"),
					rs.getInt("page_id"),
					Integer.parseInt(rs.getString("item_ids")),
					rs.getString("item_names"),
					rs.getString("specialName"),
					rs.getInt("cost_credits"),
					rs.getInt("cost_diamonds"),
					rs.getInt("cost_loyalty"),
					rs.getInt("cost_duckets"),
					rs.getInt("amounts"),
					rs.getInt("achievement"),
					rs.getInt("song_id"),
					rs.getInt("limited_sells"),
					rs.getInt("limited_stack"),
					Varoke.stringToBool(rs.getString("offer_active")),
					Varoke.stringToBool(rs.getString("club_only")),
					rs.getString("extradata"),
					rs.getString("badge"),
					rs.getInt("order_num")
					));
		}
		Varoke.getFactory().dispose(cn, ps, rs);
		return result;
	}
	public boolean checkVoucher(String code, Habbo user) throws Exception {
		Connection cn = Varoke.getDatabase().getConnection();
		PreparedStatement ps = cn.prepareStatement("SELECT * FROM vouchers WHERE code = ?");
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		boolean result = false;
		if(rs.next()){
			result = true;
			user.giveCredits(rs.getInt("credits"));
			user.giveDuckets(rs.getInt("duckets"));
			user.giveDiamonds(rs.getInt("diamonds"));
		}
		PreparedStatement ps2 = cn.prepareStatement("DELETE FROM vouchers WHERE code = ?");
		ps2.setString(1, code);
		ps2.execute();
		Varoke.getFactory().dispose(cn, ps, rs);
		ps2.close();
		ps2 = null;
		return result;
	}
	public Page getPageLayout(CatalogPage page) {
		switch(page.getLayout()){
		case "frontpage":
            return new FrontPage(page);
        case "roomads":
        	return new RoomAdsPage(page);
        case "builders_club_frontpage_normal":
            return new NormalBuildersClubFrontpage(page);
        case "bots":
            return new BotsPage(page);
        case "badge_display":
            return new BadgeDisplaysPage(page);
        case "info_loyalty":
        case "info_duckets":
            return new DucketsInfoPage(page);
        case "sold_ltd_items":
            return new LimitedSoldPage(page);
        case "recycler_info":
            return new RecyclerInfoPage(page);
        case "recycler_prizes":
            return new RecyclerPrizesPage(page);
        case "spaces_new":
        case "spaces":
            return new SpacesPage(page);
        case "recycler":
            return new RecyclerPage(page);
        case "trophies":
            return new ThrophiesPage(page);
        case "pets":
        case "pets2":
        case "pets3":
            return new PetsPage(page);
        case "soundmachine":
            return new SoundmachinePage(page);
        case "vip_buy":
            return new VIPPage(page);
        case "guild_custom_furni":
            return new GuildCustomPage(page);
        case "guild_frontpage":
            return new GuildFrontPage(page);
        case "guild_forum":
            return new GuildForumPage(page);
        case "club_gifts":
            return new ClubGiftsPage(page);
        case "default_3x3":
            return new DefaultPage(page);
        default:
            return new DefaultPage(page);
        case "builders_3x3":
            return new DefaultBuildersClubPage(page);
        case "builders_3x3_color":
            return new BuildersColorPage(page);
        case "builders_club_frontpage":
            return new BuildersClubFrontPage(page);
        case "builders_club_addons":
            return new BuildersClubAddOnsPage(page);
        case "builders_club_addons_normal":
            return new NormalBuildersClubAddOnsPage(page);
		}
	}
}
