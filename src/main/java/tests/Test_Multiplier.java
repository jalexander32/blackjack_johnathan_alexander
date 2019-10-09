package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import wager.Multiplier;
import static wager.Multiplier.Multi;

public class Test_Multiplier {
	@Test
	public void test_multi_blackjack(){
		assertEquals(Multi.BLACKJACK.getMultiplier(), 2.0);
		assertEquals(Multi.WIN.getMultiplier(), 1.5);
		assertEquals(Multi.TIE.getMultiplier(), 1.0);
		assertEquals(Multi.LOSS.getMultiplier(), 0.0);
	}
}
