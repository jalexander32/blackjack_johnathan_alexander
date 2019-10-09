package game;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import domain.Card;
import domain.Player;
import domain.Player.Status;
import exceptions.DeckException;
import exceptions.GameException;
import exceptions.WagerException;
import managers.GameManager;
import wager.Multiplier.Multi;

/**
 * Activates the console application side of the game
 *
 */
public class ConsoleApp extends GameManager{
	private boolean cont = true;
	
	private Mode mode = Mode.HAND_NOT_ACTIVE;
	private Winner winner = Winner.NONE;
	
	private String action = "";
	private String subAction = "";
	private final Scanner keyboard = new Scanner(System.in);
	public ConsoleApp() throws IOException, ClassNotFoundException, SQLException {
		super();
	}
	public void returnToDefault() {
		mode = Mode.HAND_NOT_ACTIVE;
	}
	public void initialize() throws SQLException {
		login();
		load();
	}
	public void login() throws SQLException {
		//acts as a login interface
		if(player.getUsername() == null) {
			aPrint("Please enter username. If creating a new user, enter new username: ");
			player.setUsername(keyboard.nextLine());
			initialize();
		}
	}
	/**
	 * The start of the application (other than the main method)
	 * @throws IOException
	 * @throws SQLException
	 * @throws DeckException
	 * @throws GameException
	 * @throws WagerException
	 * @throws java.lang.InterruptedException 
	 */
	public void execute() throws IOException, SQLException, DeckException, GameException, WagerException, java.lang.InterruptedException{
		initialize();
		print("Welcome To The Console Blackjack Game");
		do {
			returnToDefault();
			
			//Presents user with menu and gets any potential need for a re-iteration if there
			//is an issue with user input
			String tempAction = getAction();
			if(tempAction.equals("continue")) {
				continue;
			}
			if(subAction.equals("new_iteration_required")) {
				continue;
			}
			do {
				
				if(tempAction.equals("1")) {
					playerTurn();
					
				}
			}while(!action.equals("3"));
			checkForWinner("game");
		}while(cont);
	}
	/**
	 * Maps the game play based on user input
	 * @param action The input from the player used to determine which path to take in the game
	 * @param p A temporary player object used for turn management
	 * @throws IOException
	 * @throws SQLException
	 * @throws DeckException
	 * @throws GameException
	 */
	public void executeAction(String action, Player p) throws IOException, SQLException, DeckException, GameException {
		switch(action) {
			case "1":
				action = "1";
				//hand should not be active calling this
				if(mode == Mode.HAND_ACTIVE) {
						throw new GameException("An invalid action has occurred");
				}
				
				mode = Mode.HAND_ACTIVE;
				
				//This is done this way to simulate a dealer switching between players.
				player.addToHand(localDeck.pullCard());
				dealer.addToHand(localDeck.pullCard());
				player.addToHand(localDeck.pullCard());
				dealer.addToHand(localDeck.pullCard());
				break;
			case "2":
				action = "2";
				if(mode == Mode.HAND_NOT_ACTIVE)
					throw new GameException("An invalid action has occurred");
				p.addToHand(localDeck.pullCard());
				
				checkHand(p);
				break;
			case "3":
				action = "3";
				if(mode == Mode.HAND_NOT_ACTIVE)
					throw new GameException("An invalid action has occurred");
				mode = Mode.STAND;
				
				dealerTurn();
				checkForWinner("game");
				break;
			case "4":
				action = "4";
				exit();
				break;
			case "5":
				action = "5";
				subAction = "new_iteration_required";
				print("________All User Data____________");
				print("User Id: " + player.getId());
				print("Username: " + player.getUsername());
				print("Number of Wins: " + player.getWins());
				print("Number of Losses: " + player.getLoss());
				print("Number of Ties: " + player.getTie());
				print("Current Account Balance: $" + player.getAccount().getBalance());
				print("_________________________________");
				break;
			default:
				print("An invalid action has been entered.");
			}
			
	}
	public String getAction() throws IOException, SQLException, DeckException, GameException, WagerException {
		printMainMenu();
		
		action = keyboard.nextLine();
		
		try {
			executeAction(action, null);//gets initial action to start a new hand
		}catch(GameException e) {
			print("Please ensure you are using the correct format for console input.");
			print("All menu options should be input using the associated integer listed in the menu.");
			return "continue";//requires new iteration to reset
		}
		
		
		return action;
	}
	/**
	 * Dynamically displays dealer/player cards based on who is required to show.  Parameters include 'dealer', 'dealerMaster', 'player', and 'both'.
	 * @param who The incoming parameter that decides which cards to show.
	 */
	public void showHands(String who) {
		
		List<Card> playerHand = player.getHand();
		List<Card> dealerHand = dealer.getHand();
		if(who.equals("dealer") || who.contentEquals("both") || who.equals("dealerMaster")) {
			aPrint("Dealer's Cards: ");
			int cardIndex = 0;//declared to determine if all cards are shown for the dealer or if one is hidden
			if(!who.equals("dealerMaster")) {
				aPrint("X ");
				cardIndex = 1;
			}
			
			for(int i = cardIndex; i < dealerHand.size(); i++) {
				aPrint(dealerHand.get(i).getCardFace() + " ");
			}
			if(who.equals("dealerMaster")) {
				print("");//create a hard return
			}
		}
		if(who.equals("player") || who.equals("both")) {
			aPrint(player.getUsername() + "'s Cards: ");
			
			playerHand.forEach((card) -> aPrint(card.getCardFace() + " "));
			
			print("");//new line
		}
	}
	/**
	 * Initial Game Menu
	 */
	public void printMainMenu() {
		print("MENU");
		print("1. Deal Hand");
		print("4. Exit");
		print("5. Print User Data");
		aPrint("Choose an option by typing the associated integer : ");
	}
	/**
	 * In Game Menu
	 */
	public void printGameMenu() {
		print("MENU");
		print("2. Hit");
		print("3. Stand");
		aPrint("Choose an option by typing the associated integer   ");
	}
	public void activateDealerAI() {
		
	}
	/**
	 * Determines the winner of the round
	 * @throws DeckException
	 */
	public void getWinner() throws DeckException {
		if(player.hasTwentyOne() && dealer.hasTwentyOne()) {
			winner = Winner.TIE;
			return;
		}
		if(player.hasTwentyOne()) {
			winner = Winner.PLAYER;
			return;
		}
		if(dealer.hasTwentyOne()) {
			winner = Winner.DEALER;
			return;
		}
		if((player.getTotalHandValue() > dealer.getTotalHandValue()) && player.getTotalHandValue() <= 21) {
			winner = Winner.PLAYER;
			return;
		}
		winner = Winner.DEALER;
	}
	public void hit(Player p) throws DeckException{
		p.addToHand(localDeck.pullCard());
		checkHand(p);
		
	}
	public void print(String message) {
		System.out.println(message);
	}
	public void aPrint(String message) {
		System.out.print(message);
	}
	/**
	 * Executes the player's turn
	 * @throws IOException
	 * @throws SQLException
	 * @throws DeckException
	 * @throws GameException
	 * @throws WagerException 
	 * @throws java.lang.InterruptedException 
	 */
	public void playerTurn() throws IOException, SQLException, DeckException, GameException, WagerException, java.lang.InterruptedException{
		print(player.getUsername().toUpperCase()+"'S TURN");
		while(!action.equals("3") || !action.equals("4")) {
			int wager = 0;
			
			if(action.equals("1")) {
				action = "";
				//Perform wager actions
				print("Current Account Balance: " + player.getAccount().getBalance());
				aPrint("Place Your Wager (integer form only): ");
				String wagerStr = keyboard.nextLine();
				try {
					wager = Integer.parseInt(wagerStr);						
				}catch(Exception e) {
					print("Invalid wager format.  Enter a wager greater than 0 and in integer format only.");
					playerTurn();
				}
	
				getWager().wager(wager);
			}
			showHands("both");
			printGameMenu();
			String act = keyboard.nextLine();
			print(act);
			executeAction(act, player);
			//showHands("both");
			
			if(player.getStatus() == Status.BUST) {
				print("Sorry.  You Busted");
				player.incrementLoss();
				dealer.incrementWins();
				return;
			}
		}
		print("END " + player.getUsername().toUpperCase() + "'S TURN");
	}
	/**
	 * Executes the dealer's turn using a simulation
	 * @throws DeckException 
	 * @throws GameException 
	 * @throws SQLException 
	 * @throws IOException 
	 */
	public void dealerTurn() throws DeckException, IOException, SQLException, GameException {
		print("DEALER'S TURN");
		showHands("dealerMaster");
		checkForWinner("init");//Only looking for an initial 21 value
		
		while(dealer.dealerHit()) {
			print("DEALER HITS");
			executeAction("2", dealer);
			showHands("dealerMaster");
			checkHand(dealer);
			checkForWinner("game");
		}
		print("END DEALER'S TURN");
	}
	/**
	 * Checks if the associated hand has 21 or greater
	 * @param p
	 */
	public void checkHand(Player p) {
		if(p.getTotalHandValue() > 21) {
			p.setStatus(Status.BUST);
		}
	}
	public void handleWinner() {
		if(winner != Winner.NONE) {
			return;
		}
		switch(winner) {
			case DEALER:
				dealer.incrementWins();
				player.incrementLoss();
				break;
			case PLAYER:
				dealer.incrementLoss();
				player.incrementWins();
				wager.win(Multi.WIN);
				break;
			case TIE:
				dealer.incrementTie();
				player.incrementTie();
			default:
				break;
		}
	}
	/**
	 * Attempts to determine if a winner can be declared
	 * @param str A string parameter that allows the method to know which conditionals to execute.
	 * @throws DeckException
	 */
	public void checkForWinner(String str) throws DeckException {
		if(str.equals("init") || str.equals("game")) {
			if(dealer.hasTwentyOne() && player.hasTwentyOne()) {
				winner = Winner.TIE;
			}
			if(dealer.hasTwentyOne() && !player.hasTwentyOne()) {
				winner = Winner.DEALER;
			}
			if(!dealer.hasTwentyOne() && player.hasTwentyOne()) {
				wager.win(Multi.BLACKJACK);
				winner = Winner.PLAYER;
			}
		}
		if(str.equals("game")) {
			if((dealer.getTotalHandValue() > player.getTotalHandValue()) && dealer.getTotalHandValue() <= 21)
				winner = Winner.DEALER;
			if((player.getTotalHandValue() > dealer.getTotalHandValue()) && player.getTotalHandValue() <= 21)
				winner = Winner.PLAYER;
			if((player.getTotalHandValue() == dealer.getTotalHandValue()) && player.getTotalHandValue() <= 21)
				winner = Winner.TIE;
		}
	}
}
