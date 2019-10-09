package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domain.Card;
import domain.Deck;
import exceptions.DeckException;

public class Test_Cards {
	Deck test_deck = Deck.getInstance();
	
	@Test
	public void test_draw_card() throws DeckException{
		test_deck.shuffleDeck();
		assertEquals(test_deck.pullCard().getClass(), Card.class);
	}

}
