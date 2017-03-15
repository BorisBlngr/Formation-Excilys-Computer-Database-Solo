package com.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum PersistenceManager {

	INSTANCE;
	
	final Logger logger = LoggerFactory.getLogger(PersistenceManager.class);
	final Properties prop = new Properties();
	InputStream input = null;


	// Connection conn = null;
	// Statement stmt = null;
	// ResultSet rs = null;

	private PersistenceManager() {
		try {
			input = new FileInputStream("src.main/resource/conf.properties");
			
			prop.load(input);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Methode se connecter à DB_URL et renvoi la Connection.
	 * 
	 */
	public Connection connectToDb() {
		Connection conn = null;
		// Register JDBC driver
		try {
			System.out.println(prop.getProperty("jdbc.driver"));
			Class.forName(prop.getProperty("jdbc.driver"));
			// Open a connection
			logger.info("Connecting to db .... ");
			conn = DriverManager.getConnection(prop.getProperty("DB_URL"), prop.getProperty("USER"), prop.getProperty("PASS"));
			logger.info("Connection opened");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Methode pour faire un sendQuery avec l'instruction sql. Renvoit un
	 * ResultSet. No protection against sql injection.
	 * 
	 * @param sql
	 *            Chaine à exécuter.
	 * @return resultSet
	 */
	@Deprecated
	public ResultSet sendQuery(String sql) {
		Connection conn = connectToDb();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			
			stmt = conn.createStatement();
			logger.info("SendQuery : {}", sql);
			rs = stmt.executeQuery(sql);
			
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Methode pour faire un execute(sql). Renvoit le result. No protection against sql protection.
	 * 
	 * @param id
	 *            L'id du computer à trouver.
	 * @return computer
	 */
	@Deprecated
	public boolean sendToExec(String sql) {
		Connection conn = connectToDb();
		Statement stmt = null;
		boolean result = false;
		try {
			stmt = conn.createStatement();
			logger.info("execute : {}", sql);
			result = stmt.execute(sql);
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}