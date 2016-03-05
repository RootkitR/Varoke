package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.utils.Configuration;
import ch.rootkit.varoke.web.cookie.Cookie;

public class HotelHandler implements WebHandler {

	@Override
	public byte[] handle(HttpExchange he, HashMap<String, HashMap<String, String>> arguments, Cookie cookie) {
		Habbo bySSO = Varoke.getWeb().getUserManager().getHabbo(cookie.getCookie("user_id"));
		if(bySSO == null){
			return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/\">".getBytes();
		}
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("sso", bySSO.getSSO());
		values.put("host", Configuration.get("tcp.ip"));
		values.put("port", Configuration.get("tcp.port"));
		return Varoke.getWeb().fillAsset(values, "client.html");
	}

}
