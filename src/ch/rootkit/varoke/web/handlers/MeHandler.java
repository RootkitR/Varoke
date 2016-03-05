package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.web.cookie.Cookie;

public class MeHandler implements WebHandler {

	@Override
	public byte[] handle(HttpExchange he, HashMap<String, HashMap<String, String>> arguments, Cookie cookie) {
		Habbo bySSO = Varoke.getWeb().getUserManager().getHabbo(cookie.getCookie("user_id"));
		if(bySSO == null){
			return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/\">".getBytes();
		}
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("usercount", Varoke.getSessionManager().size());
		values.put("username", bySSO.getUsername());
		values.put("credits", bySSO.getCredits());
		values.put("diamonds", bySSO.getDiamonds());
		values.put("duckets", bySSO.getCredits());
		values.put("figure", bySSO.getLook());
		values.put("motto", bySSO.getMotto());
		return Varoke.getWeb().fillAsset(values, "me.html");
	}
}
