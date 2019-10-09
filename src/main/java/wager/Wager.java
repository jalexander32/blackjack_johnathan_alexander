package wager;

import static managers.GameManager.getPlayer;
import static managers.Manager.increaseFunds;

import java.sql.SQLException;

import domain.Account;
import exceptions.WagerException;
import wager.Multiplier.Multi;

public class Wager{
	private static Account account = getPlayer().getAccount();
	private int currentWager = 0;
	private static Wager instance = new Wager();
	
	private Wager(){
		
	}
	
	/**
	 * @param wager The whole dollar amount wagered by the user.
	 * @throws SQLException 
	 * @throws InterruptedException 
	 */
	public void wager(int wager) throws WagerException, SQLException, InterruptedException{
		if(account.getBalance() < wager){
			increaseFunds();
			System.out.println("A generous donor has added funds to your account.");
			System.out.println("Make sure you show gratitude to Mr. Rockefeller.");
		}
		
		account.lose(wager);//places initial wager
		

	}
	public static Wager getInstance(){
		instance = new Wager();
		return instance;
	}
	private void reset() {
		instance = new Wager();
	}
	/**
	 * @param multiplier The multiplier enum set by Blackjack.java
	 */
	public void win(Multi multiplier) {
		int winnings = (int) (currentWager * multiplier.getMultiplier());
		account.gain(winnings);
	}
	public int getCurrentWager(){
		return currentWager;
	}
	public void setCurrentWager(int currentWager){
		this.currentWager = currentWager;
	}
}
