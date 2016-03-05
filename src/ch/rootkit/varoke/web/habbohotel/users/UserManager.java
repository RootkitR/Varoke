package ch.rootkit.varoke.web.habbohotel.users;

import ch.rootkit.varoke.Varoke;
import ch.rootkit.varoke.habbohotel.users.Habbo;

public class UserManager {

	public UserManager(){
		
	}
	
	public boolean canLogin(String username, String password) {
		try {
			return Varoke.getFactory().getUserFactory().canLogin(username, password);
		} catch (Exception e) {/* VERY VERY BAD FROM ME SORRY */}
		return false;
	}

	public Habbo getHabbo(String id) {
		try {
			return Varoke.getFactory().getUserFactory().getHabbo(Integer.parseInt(id));
		} catch (Exception e) { /* STILL VERY VERY BAD!! */}
		return null;
	}
	
	public int getIdByUsername(String username){
		try{
			return Varoke.getFactory().getUserFactory().getIdFromUser(username);
		} catch(Exception e){ /*BAD*/}
		return 0;
	}

	public void updateSSO(String Id, String username) {
		try {
			Varoke.getFactory().getUserFactory().updateValue(Integer.parseInt(Id), "sso", Varoke.getWeb().createSSO(username));
		} catch(Exception ex){
			/*BAD*/
		}
	}

	public boolean exists(String username) {
		try {
			return Varoke.getFactory().getUserFactory().userExists(username);
		} catch (Exception e) {
			/*STILL BAD*/
		}
		return false;
	}

	public boolean mailInUse(String mail) {
		try {
			return Varoke.getFactory().getUserFactory().getUserWhere("mail", mail) != 0;
		} catch (Exception e) {
			/*BADDDDDDD */
		}
		return false;
	}
}
