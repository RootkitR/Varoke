package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.web.cookie.Cookie;

public class IndexHandler implements WebHandler {

	@Override
	public byte[] handle(HttpExchange exchange, HashMap<String, HashMap<String,String>> arguments, Cookie cookie) {
		Habbo bySSO = Varoke.getWeb().getUserManager().getHabbo(cookie.getCookie("user_id"));
		if(bySSO != null){
			return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/me\">".getBytes();
		}
		cookie.removeCookie("user_sso");
		return new String(Varoke.getWeb().getAsset("index.html")).replace("{failed}", arguments.get("GET").containsKey("failed") ? "<h2>Login failed!</h2>" : "").getBytes();
	}
}
