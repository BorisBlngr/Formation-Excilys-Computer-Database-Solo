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


	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

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
	 * Methode se connecter à DB_URL.
	 * 
	 */
	public Connection connectToDb() {
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
	 * ResultSet.
	 * 
	 * @param sql
	 *            Chaine à exécuter.
	 * @return resultSet
	 */
	public ResultSet sendQuery(String sql) {
		try {
			stmt = conn.createStatement();
			logger.info("SendQuery : {}", sql);
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	/**
	 * Methode pour faire un execute(sql). Renvoit le result.
	 * 
	 * @param id
	 *            L'id du computer à trouver.
	 * @return computer
	 */
	public boolean sendToExec(String sql) {
		boolean result = false;
		try {
			stmt = conn.createStatement();
			logger.info("execute : {}", sql);
			result = stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Ferme la connection, le resultSet et le statement.
	 * 
	 */
	public void close() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			conn.close();
			logger.info("Connection closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConn() {
		return conn;
	}

	public Statement getStmt() {
		return stmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
}