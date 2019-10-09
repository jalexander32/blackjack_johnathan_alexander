package domain;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import exceptions.DeckException;

public class Player{
	private String username = "";
	private int id;
	private int wins = 0;
	private int loss = 0;
	private int tie = 0;
	private int totalHandValue = 0;
	
	protected List<Card> hand;
	
	private Account account;
	
	private Status status;
	public enum Status{
		BUST, WINNER, LOSER
	};
	public Player(String username) throws IOException{
		this.username = username;
		
		hand = new ArrayList<>();
		account = Account.getInstance();
	}
	public void incrementWins(){System.out.println("win");
		wins++;
	}
	public void incrementLoss(){System.out.println("loss");
		loss++;
	}
	public void incrementTie() {
		tie++;
	}
	public void addToHand(Card card){
		if(card.getAltValue() == 1){
			if(this.getTotalHandValue() + 11 <= 21)
				totalHandValue += card.getValue();
			if(this.getTotalHandValue() + 11 > 21)
				totalHandValue += card.getAltValue();
		}else{
			totalHandValue += card.getValue();
		}
		hand.add(card);
	}
	public void addToHand(Card[] cards){
		for(Card c : cards){
			addToHand(c);
		}
	}

	//for dealer hands
	public Card showSingle(){
		if(hand.size() > 0)
			return hand.get(0);
		return null;
	}
	
	public List<Card> show(){
		if(hand.size() == 0)
			return null;
		return hand;
	}
	public int aces(List<Card> hand) throws DeckException{
		if(hand.size() == 0)
			return 0;
		int ret = 0;
		
		for(Card c : hand){
			if(c.getCardFace().equals("A"))
				ret++;
		}
		
		if(ret > 4){
			throw new DeckException("Too many aces in hand");
		}
		return ret;
	}
	public void resetHand(){
		this.hand = new ArrayList<Card>();
	}
	public boolean hasTwentyOne() throws DeckException{
		if(hand.size() == 0)
			return false;
		
		int numAces = aces(hand);
		int handTotal = 0;
		for(Card c : hand){
			if(c.getCardFace().equals("A"))
				continue;
			handTotal += c.getValue();
		}
		for(int count = 0; count<numAces; count++){
			if(handTotal <= 10 && numAces == 1){
				handTotal += 11;
				break;
			}
			if(count==numAces-1 && handTotal <=10){
				handTotal += 11;
				break;
			}
			else
				handTotal+=1;
			
		}
		if(handTotal == 21)
			return true;
		return false;
	}
	public Account getAccount() {
		return account;
	}
	public Status getStatus() {
		return status;
	}
	public int getId() {
		return id;
	}
	public int getTie() {
		return tie;
	}
	public String getUsername() {
		return username;
	}
	public int getWins() {
		return wins;
	}
	public int getLoss(){
		return loss;
	}
	public int getTotalHandValue() {
		return totalHandValue;
	}
	public List<Card> getHand() {
		return hand;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setTie(int tie) {
		this.tie = tie;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public void setLoss(int loss){
		this.loss = loss;
	}
	public void setTotalHandValue(int totalHandValue) {
		this.totalHandValue = totalHandValue;
	}
	public void setHand(List<Card> hand) {
		this.hand = hand;
	}
}
