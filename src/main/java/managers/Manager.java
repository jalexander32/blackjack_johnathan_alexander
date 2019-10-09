package managers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import db.CloudDatabase;
import domain.Dealer;
import domain.Player;
import utils.GameState;
import utils.GameStateUpdater;


/**
 * Parent manager that maintains the database, the database connections,
 * additional threads, and other maintainable tasks.
 *
 */
public abstract class Manager {
	private static Thread updater;
	private static boolean isSavable = false;
	private static CloudDatabase db;
	protected final Logger logger = Logger.getLogger( Manager.class.getName() );
	
	protected static Player player;
	protected static Dealer dealer;
	
	public Manager() throws IOException, ClassNotFoundException, SQLException{
		player = new Player(null);
		dealer = new Dealer(null);
		
		db = CloudDatabase.getInstance();
		
		GameState.init();
		
		//Starts a new thread to periodically update the database
		//without interrupting gameplay (uses different thread)
		updater = new GameStateUpdater();
		updater.start();
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					save();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				System.exit(0);
			}
		});
	}
	public static void save() throws SQLException{
		db.update();
	}
	public static boolean isSavable() {
		return isSavable;
	}
	public static void increaseFunds() throws SQLException, InterruptedException {
		db.updateAccount(player.getId(), 1000);
	}
	public void load() throws SQLException{
		player = db.initGame(player);
		isSavable = true;
	}
	public CloudDatabase getCloudDatabase() {
		return db;
	}
	
	public void finalize() throws SQLException {
		logger.warning("APPLICATION HAS RECEIVED THE USER'S SHUTDOWN REQUEST");
		logger.warning("THE APPLICATION IS SHUTTING DOWN");
		if(db.isConnected())
			db.close();
		updater.interrupt();
	}
}
