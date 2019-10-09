package domain;

/**
 * Creates a virtual card object to interact with.
 *
 */
public class Card {
	private String cardFace;
	private int value;
	private int altValue;//ace only
	
	public Card(String cardFace, int value){
		this.cardFace = cardFace;
		this.value = value;
	}
	public Card(String cardFace, int value, int altValue){
		this(cardFace, value);
		setAltValue(altValue);
	}
	public String getCardFace(){
		return cardFace;
	}
	public int getValue(){
		return value;
	}
	public int getAltValue(){
		return altValue;
	}
	public void setCardFace(String cardFace){
		this.cardFace = cardFace;
	}
	public void setValue(int value){
		this.value = value;
	}
	public void setAltValue(int altValue){
		this.altValue = getCardFace().equals("A") ? altValue : 0;//prevents any card other than "A" from setting this
	}
}
