package ch.rootkit.varoke.habbohotel.exceptions;

public class PurchaseError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ErrorCode;
	
	public PurchaseError(int errorCode){
		ErrorCode = errorCode;
	}
	
	public int getErrorCode(){ 
		return ErrorCode; 
	}
}
