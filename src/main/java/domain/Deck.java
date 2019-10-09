package domain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import exceptions.DeckException;

/**
 * Creates a virtual deck of cards with associated properties and behaviors.
 *
 */
public class Deck {
	private static List<Card> deck;
	private static final Deck instance = new Deck();
	private Deck(){
		deck = new ArrayList<>();
		resetDeck();
	}
	//This is to ensure that there is only one deck instance during runtime
	public static Deck getInstance(){
		return instance;
	}
	/**
	 * Initializes deck to original state
	 */
	public void resetDeck(){
		Card[] tempDeck = new Card[] {
				new Card("A", 11, 1), new Card("2", 2), new Card("3", 3), new Card("4", 4), new Card("5", 5), new Card("6", 6),
				new Card("7", 7), new Card("8", 8), new Card("9", 9), new Card("10", 10), new Card("J", 10), new Card("Q", 10), new Card("K", 10),
				new Card("A", 11, 1), new Card("2", 2), new Card("3", 3), new Card("4", 4), new Card("5", 5), new Card("6", 6),
				new Card("7", 7), new Card("8", 8), new Card("9", 9), new Card("10", 10), new Card("J", 10), new Card("Q", 10), new Card("K", 10),
				new Card("A", 11, 1), new Card("2", 2), new Card("3", 3), new Card("4", 4), new Card("5", 5), new Card("6", 6),
				new Card("7", 7), new Card("8", 8), new Card("9", 9), new Card("10", 10), new Card("J", 10), new Card("Q", 10), new Card("K", 10),
				new Card("A", 11, 1), new Card("2", 2), new Card("3", 3), new Card("4", 4), new Card("5", 5), new Card("6", 6),
				new Card("7", 7), new Card("8", 8), new Card("9", 9), new Card("10", 10), new Card("J", 10), new Card("Q", 10), new Card("K", 10)
		};
		deck = Arrays.asList(tempDeck);
	}
	
	public void shuffleDeck(){
		//In an effort to ensure a very randomized order of the deck, shuffle three times.
		Collections.shuffle(deck);
		Collections.shuffle(deck);
		Collections.shuffle(deck);
	}
	public Card pullCard() throws DeckException{
		int index = 0;//index for card pull
		if(!checkAvailable())//checks if any cards are left in the deck
			throw new DeckException("No Cards Remaining In Deck");
		Card card = deck.get(index); //store top card;
		while(card == null){//verifies a card has been pulled
			card = deck.get(index++);
		}
		
		for(int count = 0; count < deck.size(); count++){
			if(deck.get(count) == null)
				continue;
			if(deck.get(count).getCardFace().equals(card.getCardFace())){
				deck.set(count, null);//removes card
			}
		}
		return card;
	}
	private boolean checkAvailable(){
		for(Card c : deck){
			if(c!=null)
				return true;
		}
		return false;
	}
}
