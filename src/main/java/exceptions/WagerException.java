package exceptions;

@SuppressWarnings("serial")
public class WagerException extends Exception {
	private String message;
	
	private final static String DEFAULT_EXCEPTION_MESSAGE = "A wagering exception has occurred";
	
	public WagerException(){
		super(DEFAULT_EXCEPTION_MESSAGE);
		message = DEFAULT_EXCEPTION_MESSAGE;
	}
	public WagerException(String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
	public void printStackTrace(){
		super.printStackTrace();
	}
}
