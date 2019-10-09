package managers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


import domain.Deck;
import domain.Player;
import wager.Wager;


public abstract class GameManager extends Manager{
	
	
	protected static Wager wager;
	
	protected final Deck localDeck;
	protected static final Scanner keyboard = new Scanner(System.in);
	
	public GameManager() throws IOException, ClassNotFoundException, SQLException{
		super();
		wager = Wager.getInstance();
		localDeck = Deck.getInstance();
		localDeck.shuffleDeck();
		
	}
	public enum Mode{
		HAND_ACTIVE, HAND_NOT_ACTIVE, STAND
	};
	public enum Winner{
		NONE, PLAYER, DEALER, TIE
	};
	
	//edit with ui for emergency save but shouldn't be necessary
	public void InterruptedException() throws SQLException{
		logger.warning("An interruption has occurred in the application.");
		save();
		//db.close();
	}
	//To Implement
	protected void exit() throws IOException, SQLException{		
		System.out.println("Data saved.  Exiting.");
		System.out.println("Have a good day!");
				
		keyboard.close();
		
		save();
		
		finalize();
		
		System.exit(0);
	}
	public static Player getPlayer() {
		return player;
	}
	public static Player getDealer() {
		return dealer;
	}
	public static Wager getWager() {
		return wager;
	}
}
