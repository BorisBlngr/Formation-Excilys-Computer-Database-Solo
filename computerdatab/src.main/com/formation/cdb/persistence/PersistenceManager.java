package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PersistenceManager {

	final Logger logger = LoggerFactory.getLogger(PersistenceManager.class);

	private static PersistenceManager INSTANCE = new PersistenceManager();
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jc.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/computer-database-db?zeroDateTimeBehavior=convertToNull";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	private PersistenceManager() {
	}

	/**
	 * Methode se connecter à DB_URL.
	 * 
	 */
	public void connectToDb() {
		// Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Open a connection
			logger.info("Connecting to db .... ");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			logger.info("Connection opened");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static PersistenceManager getInstance() {
		return INSTANCE;
	}
}