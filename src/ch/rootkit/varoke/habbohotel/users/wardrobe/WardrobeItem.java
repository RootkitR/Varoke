package ch.rootkit.varoke.habbohotel.users.wardrobe;

public class WardrobeItem {
	private int Slot;
	private String Look;
	private String Gender;
	public WardrobeItem(int slot, String look, String gender){
		Slot = slot;
		Look = look;
		Gender = gender;
	}
	public int getSlot(){ return Slot;}
	public String getLook(){ return Look;}
	public String getGender(){ return Gender;}
}
