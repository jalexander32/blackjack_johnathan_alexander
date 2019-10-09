package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Logger;

import utils.YamlParser;

/**
 * An abstract class designed to allow for additional database usage in the future other than AWS RDS
 *
 */
public abstract class Database {
	Logger LOG = Logger.getLogger(CloudDatabase.class.getName());
	
	protected boolean isConnected = false;
	
	protected Map<String, Object> config;
	protected Connection conn;
	
	/*
	 * Initializes the database to retrieve configuration settings and verify drivers
	 * @throws ClassNotFoundException
	 */
	protected void initialize() throws ClassNotFoundException {
		LOG.info("LOADING DATABASE INFORMATION");
		YamlParser parser = new YamlParser("database");
		config = parser.getMapping();
		
		LOG.info("DATABASE INFORMATION LOADED SUCCESSFULLY");
		LOG.info("LOADING DRIVER");
		
		Class.forName("org.postgresql.Driver"); //can move this string to config and allow a loop to allow verification of multiple drivers
		LOG.info("DRIVER LOADED SUCCESSFULLY");
	}
	public boolean isConnected() {
		return isConnected;
	}
	public abstract void connect() throws SQLException;
	public abstract void update() throws SQLException;
}
