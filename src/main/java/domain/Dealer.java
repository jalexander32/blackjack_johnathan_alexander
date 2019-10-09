package domain;

import java.io.IOException;
import java.util.Map;

import exceptions.DeckException;
import utils.YamlParser;
/**
 * The Dealer class handles any specialized actions for the A.I. dealer character.
 */
public class Dealer extends Player {
	Map<String, Object> config = (Map<String, Object>) new YamlParser("dealer").getMapping();
	public Dealer(String dealerName) throws IOException {
		super(null);
	}
	
	/**
	 * Determines whether the dealer should take another card based on config values.
	 * Default config files have the dealer stopping on hard 17 but can be changed.
	 * Where the dealer stops can be altered in the yaml configuration files.
	 * @return A boolean based on whether the dealer should accept another card. 
	 * @throws DeckException
	 */
	public boolean dealerHit() throws DeckException {
		int hardStop = (int) config.get("stays_on");
		boolean hasAce = false;
		boolean hasTen = false; //determines if dealer has a ten-value card
		
		//This aides in determining whether the value is a soft or hard value
		for(Card c : getHand()) {
			if(hasAce && hasTen)
				break;
			if(c.getValue() == 10)
				hasTen = true;
			if(c.getValue() == 11)
				hasAce = true;
		}
		if(hasTwentyOne())
			return false;
		if(getTotalHandValue() >= hardStop)
			return false;
		if(getTotalHandValue() == hardStop && hasAce && !hasTen)//soft max value
			return true;
		if(getTotalHandValue() == hardStop && !hasAce)//hard max value
			return false;
		if(getTotalHandValue() < hardStop)
			return true;
		return false;
	}
	
}
