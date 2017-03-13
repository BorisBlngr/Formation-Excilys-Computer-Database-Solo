package com.formation.cdb.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.formation.cdb.persistence.PersistenceManager;

public class ComputerDao extends Dao<Computer> {


	public ComputerDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Computer find(int id) {
		Computer computer = new Computer();

		try {
			String sql;
			sql = "SELECT * FROM computer WHERE id = " + id;
			ResultSet rs = PersistenceManager.getInstance().sendQuery(sql);
			// Extract data from result set
			if (rs.first()) {
				computer.setId(id);
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return computer;
	}

	// return a new computer with [id=0, name=null, introduced=null,
	// companyId=0, discontinued=null] if not found
	public Computer findByName(String name) {
		Computer computer = new Computer();
		String sql;
		sql = "SELECT * FROM computer WHERE name = '" + name + "'";
		ResultSet rs = PersistenceManager.getInstance().sendQuery(sql);

		try {
			if (rs.first()) {
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return computer;
	}

	public int findIdByName(String name) {
		String sql;
		int id = 0;
		sql = "SELECT * FROM computer WHERE name = '" + name + "'";
		ResultSet rs = PersistenceManager.getInstance().sendQuery(sql);

		try {
			if (rs.first()) {
				id = rs.getInt("id");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;

	}

	// Dates must be yyyy-mm-dd hh-mm-ss
	@Override
	public int create(Computer cmpt) {
		String sql;
		sql = "INSERT INTO computer(name,introduced,discontinued,company_id) VALUES ('" + cmpt.getName() + "', '"
				+ java.sql.Date.valueOf(cmpt.getIntroduced()) + "','" + java.sql.Date.valueOf(cmpt.getDiscontinued())
				+ "','" + cmpt.getCompanyid() + "')";
		PersistenceManager.getInstance().sendToExec(sql);
		System.out.println("Try to exec : " + sql);
		return findIdByName(cmpt.getName());
	}

	@Override
	public boolean delete(Computer cmpt) {
		String sql;
		sql = "DELETE FROM computer WHERE id = " + cmpt.getId();
		PersistenceManager.getInstance().sendToExec(sql);
		return false;
	}

	//Careful with the computer's id
	@Override
	public boolean update(Computer cmpt) {
		String sql;
		sql = "UPDATE computer SET name = '" + cmpt.getName() + "', introduced = '"
				+ java.sql.Date.valueOf(cmpt.getIntroduced()) + "', discontinued = '"
				+ java.sql.Date.valueOf(cmpt.getDiscontinued()) + "', company_id = " + cmpt.getCompanyid()
				+ " WHERE id = " + cmpt.getId();
		PersistenceManager.getInstance().sendToExec(sql);
		return false;
	}

}
