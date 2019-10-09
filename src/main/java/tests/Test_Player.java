package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import domain.Card;
import domain.Player;
import exceptions.DeckException;
/*
 * @Test_Player Tests the methods related to the main.Player class 
 */
public class Test_Player {	
	
	@Test(expected = DeckException.class)
	public void test_aces() throws DeckException, IOException{
		Player test_player = new Player(null);
		assert(test_player.aces(test_player.getHand()) == 0);
		for(int count = 0; count < 4; count++){
			test_player.addToHand(new Card("A", 11, 1));
		}
		
		assert(test_player.aces(test_player.getHand()) == 4);
		
		test_player.addToHand(new Card("A", 11, 1));
		
		test_player.aces(test_player.getHand());  // This should throw the expected DeckException
	}
	@Test
	public void test_show() throws IOException{
		Player test_player = new Player(null);
		Card c1 = new Card("4", 4);
		Card c2 = new Card("K", 10);
				
		List<Card> test_hand = new ArrayList<>();
		test_hand.add(c1);
		test_hand.add(c2);
		test_player.setHand(test_hand);
		
		assertEquals(test_hand, test_player.show());
		
	}
	@Test
	public void test_dealer_show_single() throws IOException {		
		Player test_dealer = new Player(null);
		
		test_dealer.addToHand(new Card("A", 11, 1));
		test_dealer.addToHand(new Card("K", 10));
		
		Card test = new Card("A", 11, 1);
		Card single = test_dealer.showSingle();
		
		assertTrue(test.getCardFace().equals(single.getCardFace()));
		assertTrue(test.getValue() == single.getValue());
		assertTrue(test.getAltValue() == single.getAltValue());
	}
		//test_has_twentyone has many test cases to verify that a wide variety
		//of situations are accounted for
		@Test
		public void test_has_twentyone() throws DeckException, IOException{
			Player test_player = new Player(null);
			
			assertFalse(test_player.hasTwentyOne()); //Test against empty hand
			
			test_player.addToHand(new Card("A", 11, 1));
			test_player.addToHand(new Card("K", 10));
			
			assertTrue(test_player.hasTwentyOne());
			
			test_player.resetHand();
			
			assertFalse(test_player.hasTwentyOne());
			test_player.addToHand(new Card("A", 11, 1));
			test_player.addToHand(new Card("A", 11, 1));
			test_player.addToHand(new Card("K", 10));
			
			assertFalse(test_player.hasTwentyOne()); //Test against 22
			
			test_player.resetHand();
			
			test_player.addToHand(new Card("5", 5));
			test_player.addToHand(new Card("9", 9));
			test_player.addToHand(new Card("7", 7));
			
			assertTrue(test_player.hasTwentyOne());
			
			test_player.resetHand();
			
			test_player.addToHand(new Card("K", 10));
			test_player.addToHand(new Card("K", 10));
			test_player.addToHand(new Card("K", 10));
			
			assertFalse(test_player.hasTwentyOne());
			
			test_player.resetHand();
			
			test_player.addToHand(new Card("2", 2));
			
			assertFalse(test_player.hasTwentyOne());
			
			test_player.resetHand();
			
			test_player.addToHand(new Card("A", 11, 1));
			test_player.addToHand(new Card("3", 3));
			test_player.addToHand(new Card("5", 5));
			test_player.addToHand(new Card("6", 6));
			
			assertFalse(test_player.hasTwentyOne());
		}

}
