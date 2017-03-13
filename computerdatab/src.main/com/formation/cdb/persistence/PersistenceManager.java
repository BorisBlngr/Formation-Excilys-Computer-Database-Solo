package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PersistenceManager {

	private static PersistenceManager INSTANCE = new PersistenceManager();
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jc.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/computer-database-db";

	// Database credentials
	static final String USER = "root";
	static final String PASS = "root";
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	private PersistenceManager() {
	}

	// Connect to the database defined with DB_URL
	public void connectToDb() {
		// Register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Open a connection
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet sendQuery(String sql) {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	public boolean sendToExec(String sql) {
		boolean result = false;
		try {
			stmt = conn.createStatement();
			result = stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	// Close the connection
	public void close() {
		// Clean-up environment
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			conn.close();
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