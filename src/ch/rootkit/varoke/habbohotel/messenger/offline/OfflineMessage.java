package ch.rootkit.varoke.habbohotel.messenger.offline;

public class OfflineMessage {
	
	private int From;
	private int To;
	private String Message;
	private long Time;
	
	public OfflineMessage(int from, int to, String message, long time){
		From = from;
		To = to;
		Message = message;
		Time = time;
	}
	
	public int getFromId(){
		return From;
	}
	
	public int getToId(){ 
		return To;
	}
	
	public String getMessage(){ 
		return Message;
	}
	
	public long getTime(){ 
		return Time;
	}
	
}
