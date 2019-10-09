package exceptions;

@SuppressWarnings("serial")
public class GameException extends Exception{
	private String message;
	
	public GameException(){
		super();
	}
	public GameException(String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
