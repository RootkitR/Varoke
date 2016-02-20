package ch.rootkit.varoke.habbohotel.navigator;

public class NavigatorSearch {

	private int Id;
	private String Value1;
	private String SearchText;
	
	public NavigatorSearch(int id, String value1, String searchText){
		Id = id;
		Value1 = value1;
		SearchText = searchText;
	}
	
	public int getId(){
		return Id;
	}
	
	public String getValue1(){ 
		return Value1;
	}
	
	public String getSearchText(){ 
		return SearchText.contains(":") ? SearchText.split(":")[1] : SearchText;
	}
}
