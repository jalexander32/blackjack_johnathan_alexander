package game;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import exceptions.DeckException;
import exceptions.GameException;
import exceptions.WagerException;

//package main;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Map;
//import java.util.logging.Logger;
//
//import db.CloudDatabase;
//import db.Database;
//import exceptions.DatabaseConnectionException;
//import exceptions.DeckException;
//import exceptions.GameException;
//import utils.YamlParser;
//import wager.Multiplier;
//import static wager.Multiplier.Multi;
///*
// * Main.class is the entry point for the application
// * The actual entry point for the real application is Blackjack.execute()
// */
//public class Main{
//	private static final Logger logger = Logger.getLogger(Main.class.getName());
//	static ConsoleApp app = null;
//	public static void main(String[] args) throws GameException, DeckException, InterruptedException, IOException, ClassNotFoundException, DatabaseConnectionException, SQLException{
//		try{
//			app = new ConsoleApp();
//			app.execute();
//		}catch(InterruptedException e){
//			logger.warning("Keyboard Interruption. Data saved.  Game exiting.");
//			app.InterruptedException();
//			System.exit(0);
//		}
//	}
//}
//@SpringBootApplication
//public class Main{
//	public static void main(String[] args) {
//		SpringApplication.run( Main.class, args );
//	}
//}
public class Main{
	static final Logger log = Logger.getLogger(Main.class.getName());
	public static void main(String[] args) throws IOException, SQLException, DeckException, GameException, WagerException, InterruptedException {
		try {
			new ConsoleApp().execute();
		}catch(ClassNotFoundException e) {
			log.warning("Fatal Error: Database driver not found.");
			log.warning("Try updating the maven dependencies");
		}catch(InterruptedException i) {
			log.warning("An interruption has occurred. Exiting");
			System.exit(0);
		}
	}
}
