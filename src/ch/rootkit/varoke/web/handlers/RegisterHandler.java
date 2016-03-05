package ch.rootkit.varoke.web.handlers;

import java.util.HashMap;

import com.sun.net.httpserver.HttpExchange;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;
import ch.rootkit.varoke.web.cookie.Cookie;

public class RegisterHandler implements WebHandler {

	@Override
	public byte[] handle(HttpExchange he, HashMap<String, HashMap<String, String>> arguments, Cookie cookie) {
		Habbo bySSO = Varoke.getWeb().getUserManager().getHabbo(cookie.getCookie("user_id"));
		if(bySSO != null){
			return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/me\">".getBytes();
		}
		HashMap<String, Object> values = new HashMap<String, Object>();
		values.put("error", "");
		if(arguments.get("POST").containsKey("username")){
			String username = arguments.get("POST").get("username");
			String password = arguments.get("POST").get("password");
			String password2 = arguments.get("POST").get("password2");
			String mail = arguments.get("POST").get("mail");
			if(password.equals(password2) && !Varoke.getWeb().getUserManager().exists(username) && password.length() > 5 && !Varoke.getWeb().getUserManager().mailInUse(mail) && Varoke.getWeb().checkName(username) && Varoke.getWeb().checkMail(mail)){
				try {
					int Id = Varoke.getFactory().getUserFactory().register(username, Varoke.getWeb().sha256(password), mail);
					cookie.addCookie("user_id", Id + "");
					Varoke.getWeb().getUserManager().updateSSO(cookie.getCookie("user_id"),  username);
					return "<META HTTP-EQUIV=REFRESH CONTENT=\"0; URL=/me\">".getBytes();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				if(!password.equals(password2)){
					values.put("error", "Passwords do not match!");
				}
				if(Varoke.getWeb().getUserManager().exists(username)){
					values.put("error", "There's already a User with this Username!");
				}
				if(password.length() < 5){
					values.put("error", "Password is too short!");
				}
				if(Varoke.getWeb().getUserManager().mailInUse(mail)){
					values.put("error", "This E-Mail Adress is already in use!");
				}
				if(!Varoke.getWeb().checkName(username)){
					values.put("error", "Invalid Username");
				}
				if(!Varoke.getWeb().checkMail(mail)){
					values.put("error", "Invalid Mail");
				}
			}
		}
		return Varoke.getWeb().fillAsset(values, "register.html");
	}

}
