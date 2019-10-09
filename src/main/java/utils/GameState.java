package utils;

import java.util.Properties;


/**
 * 
 * Stores the game save data
 *
 */
public class GameState{	
	private static GameState instance = new GameState();
	
	//AI Player
	private int dealerScore; //tracks wins only
	
	//Human Player
	private int id;
	private int balance;
	private int wins;
	private int loss;
	private int tie;
	private String username;
	
	private GameState(){
		//do nothing
	}
	public void setDealerScore(int dealerScore) {
		this.dealerScore = dealerScore;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public void setLoss(int loss){
		this.loss = loss;
	}
	public void setTie(int tie) {
		this.tie = tie;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setId(int id) {
		this.id = id;
	}
	public static GameState getInstance(){
		return instance;
	}
	public static void init() {
		instance = new GameState();
	}
	/**
	 * 
	 * @return A Properties object storing all values to be saved.
	 */
	public Properties compileState(){
		Properties properties = new Properties();

		properties.put("user_id", id);
		properties.put("dealerScore", dealerScore);
		properties.put("balance", balance);
		properties.put("wins", wins);
		properties.put("loss", loss);
		properties.put("tie", tie);
		properties.put("username", username);
		
		return properties;
	}
	
}
