package utils;

import static managers.GameManager.getPlayer;
import static managers.GameManager.getDealer;
import static managers.Manager.save;
import static managers.Manager.isSavable;

import java.sql.SQLException;
import java.util.Map;

import org.jboss.logging.Logger;

import domain.Player;

/**
 * Designed to be able to update the game state at a regular interval without interrupting gameplay
 * 
 */
public class GameStateUpdater extends Thread{
	private final Logger log = Logger.getLogger(GameStateUpdater.class.getName());
	Map<String, Object> config = new YamlParser("updater").getMapping();
	Player dealer, player;
	@Override
	public void run() {
		while(true) {
			GameState state = GameState.getInstance();
			
			//retrieve updated objects
			dealer = getDealer();
			player = getPlayer();
			
			state.setId(player.getId());
			state.setDealerScore(dealer.getWins());
			state.setBalance(player.getAccount().getBalance());
			state.setWins(player.getWins());
			state.setLoss(player.getLoss());
			state.setTie(player.getTie());
			state.setUsername(player.getUsername());
			
			try {
				if(isSavable())
					save();
				Thread.sleep((Integer)config.get("concurrent_delay")); //Retrieves the default iteration delay
			}catch(SQLException e) {
				log.warn("IMPORTANT DATABASE ERROR: DATABASE IS NOT BEING UPDATED");
				log.warn("You can continue playing the game but the database may not update.  This error may fix itself.");
				log.warn("If you continue receiving these messages, please re-initialize the application.");
				log.warn("SQLException Message: " + e.getMessage());
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
