package domain;

public class Account {
	private int balance;
	
	private static Account account;
	
	private Account(){
		balance = 0;
	}
	
	public static Account getInstance(){
		account = new Account();
		return account;
	}
	
	public void gain(int gainAmount){
		balance += gainAmount;
	}
	public void lose(int lossAmount){
		balance -= lossAmount;
	}
	
	public int getBalance(){
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
}
