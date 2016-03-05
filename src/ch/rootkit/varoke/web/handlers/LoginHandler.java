package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.web.cookie.Cookie;

public class LoginHandler implements WebHandler {

	@Override
	public byte[] handle(HttpExchange he, HashMap<String, HashMap<String, String>> arguments, Cookie cookie) {
		Habbo bySSO = Varoke.getWeb().getUserManager().getHabbo(cookie.getCookie("user_id"));
		if(bySSO != null){
			return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/me\">".getBytes();
		}
		String username = arguments.get("POST").get("username");
		String password = Varoke.getWeb().sha256(arguments.get("POST").get("password"));
		boolean canLogin = Varoke.getWeb().getUserManager().canLogin(username, password);
		if(canLogin){
			cookie.addCookie("user_id", Varoke.getWeb().getUserManager().getIdByUsername(username) + "");
			try {
				Varoke.getWeb().getUserManager().updateSSO(cookie.getCookie("user_id"),  username);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/me\">".getBytes();
		}
		return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/?failed=true\">".getBytes();
	}

}
