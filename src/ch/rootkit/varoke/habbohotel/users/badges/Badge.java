package ch.rootkit.varoke.habbohotel.users.badges;

public class Badge {

	private String Code;
	private int Slot;
	
	public Badge(String code, int slot){
		Code = code;
		Slot = slot;
	}
	
	public String getCode(){ 
		return Code;
	}
	
	public int getSlot(){ 
		return Slot;
	}
	
	public void setSlot(int s){ 
		Slot = s;
	}
}
