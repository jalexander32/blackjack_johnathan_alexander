package db;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import domain.Account;
import domain.Player;
import utils.GameState;

/**
 * 
 * Implements an AWS RDS database using PostgreSQL
 *
 */ 
public class CloudDatabase extends Database{
	Logger log = Logger.getLogger(CloudDatabase.class.getName());
	private static CloudDatabase instance;
	private CloudDatabase() throws ClassNotFoundException, SQLException {
		initialize();//crucial
		log.info("Testing Database Connection");
		connect();//test connection
		
		close();
		log.info("Database Connection Successful");
	}
	public static CloudDatabase getInstance() throws ClassNotFoundException, SQLException {
		instance = new CloudDatabase();
		return instance;
	}
	
	@Override
	public void connect() throws SQLException {
		
		StringBuilder url = new StringBuilder();
		url.append("jdbc:postgresql://");//(String) config.get("DB_STARTER")
		url.append((String) config.get("RDS_HOSTNAME") + ":");
		url.append(config.get("RDS_PORT") + "/");
		url.append("blackjack");//(String) config.get("RDS_DB_NAME")
		conn = DriverManager.getConnection(url.toString(), 
													(String) config.get("RDS_USERNAME"),
													(String) config.get("RDS_PASSWORD"));
		
		
	}
	/**
	 * Closes current database connection
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if(!conn.isClosed())
			conn.close();
	}

	public void verifyConnection() throws SQLException {
		if(conn.isClosed()) {
			connect();
		}
	}
	public Player initGame(Player player) throws SQLException {
		verifyConnection();
		player = getPlayer(player);
		close();
		
		return player;
	}
	private Player getPlayer(Player player) throws SQLException {
		verifyConnection();
		
		String userQuery = "SELECT * FROM player WHERE username = ?";
		String accountQuery = "SELECT * FROM account WHERE user_id = ?";
		
		PreparedStatement stmt = conn.prepareStatement(userQuery);
		stmt.setString(1, player.getUsername());
		
		ResultSet set = stmt.executeQuery();
		if(!set.isBeforeFirst()) {
			insert(player);
			verifyConnection();
			initGame(player);
		}
		while(set.next()) {
			player.setId(set.getInt("id"));
			player.setWins(set.getInt("wins"));
			player.setLoss(set.getInt("losses"));
			player.setTie(set.getInt("tie"));
		}
		verifyConnection();
		
		stmt = conn.prepareStatement(accountQuery);
		stmt.setInt(1, player.getId());
		
		set = stmt.executeQuery();
		
		while(set.next()) {
			if(set.equals(null)) {
				break;
			}
				
			Account acct = player.getAccount();
			acct.setBalance(set.getInt("balance"));
			
			player.setAccount(acct);
		}
		close();
		return player;
	}
	//create user and account
	public void insert(Player p) throws SQLException {
		verifyConnection();
		
		String insertPlayerQuery = "INSERT INTO player(username) VALUES(?);";
		String insertAccountQuery = "INSERT INTO account(user_id) VALUES(?);";
		
		PreparedStatement stmt = conn.prepareStatement(insertPlayerQuery);
		stmt.setString(1, p.getUsername());
		stmt.execute();
		
		p = getPlayer(p);
		
		verifyConnection();
		
		stmt = conn.prepareStatement(insertAccountQuery);
		stmt.setInt(1,  p.getId());
		
		stmt.execute();
				
		close();
	}
	/**
	 * Executes a complete update of the database of the current state of the game
	 * @throws SQLException 
	 */
	public void update() throws SQLException {
		verifyConnection();
		
		Properties currentState = GameState.getInstance().compileState();
		
		updatePlayer((int) currentState.get("user_id"), (String) currentState.get("username"),
					 (int) currentState.get("wins"), (int) currentState.get("loss"), (int) currentState.get("tie"));
		
		updateAccount((int) currentState.get("user_id"), (int) currentState.get("balance"));
		
		close();
	}
	/**
	 * Updates the player table. Called by the update() method.
	 * @param user_id
	 * @param username
	 * @param wins
	 * @param losses
	 * @throws SQLException 
	 */
	private void updatePlayer(int user_id, String username, int wins, int losses, int tie) throws SQLException {
		verifyConnection();
		
		String updateQuery = "UPDATE player SET wins = ?, losses = ?, tie = ? WHERE username = ? AND id = ?;";
		
		PreparedStatement stmt = conn.prepareStatement(updateQuery);
		stmt.setInt(1,  wins);
		stmt.setInt(2,  losses);
		stmt.setInt(3, tie);
		stmt.setString(4, username);
		stmt.setInt(5,  user_id);
		
		stmt.executeUpdate();
		
	}
	/**
	 * Updates the account table.  Called by the update() method except to reset funding when user is out.
	 * @param id
	 * @param user_id
	 * @param balance
	 * @throws SQLException 
	 */
	public void updateAccount(int user_id, int balance) throws SQLException {
		verifyConnection();
		
		String updateQuery = "UPDATE account SET balance = ? WHERE user_id = ?;";
		
		PreparedStatement stmt = conn.prepareStatement(updateQuery);
		stmt.setInt(1, balance);
		stmt.setInt(2, user_id);
		
		stmt.executeUpdate();
		
	}
	
}
