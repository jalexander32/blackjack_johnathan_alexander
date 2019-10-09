package exceptions;

@SuppressWarnings("serial")
public class DeckException extends Exception{
	private String message;
	
	public DeckException(){
		super();
	}
	public DeckException(String message){
		super(message);
		this.message = message;
	}
	
	public String getMessage(){
		return message;
	}
}
