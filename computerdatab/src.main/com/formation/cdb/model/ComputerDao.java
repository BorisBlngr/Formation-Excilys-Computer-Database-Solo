package com.formation.cdb.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

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
		// java.sql.Date.valueOf(cmpt.getDiscontinued())
		// sql = "INSERT INTO computer(name,introduced,discontinued,company_id)
		// VALUES ('" + cmpt.getName() + "', '"
		// + java.sql.Date.valueOf(cmpt.getIntroduced()) + "','"
		// + java.sql.Date.valueOf(cmpt.getDiscontinued()) + "','" +
		// cmpt.getCompanyid() + "')";
		try {
			PreparedStatement preparedStatement = PersistenceManager.getInstance().getConn()
					.prepareStatement("INSERT INTO computer(name,introduced,discontinued,company_id) VALUES (?,?,?,?)");

			preparedStatement.setString(1, cmpt.getName());
			if (cmpt.getIntroduced() == null)
				preparedStatement.setNull(2, Types.TIMESTAMP);
			else
				preparedStatement.setDate(2, java.sql.Date.valueOf(cmpt.getIntroduced()));
			if (cmpt.getDiscontinued() == null)
				preparedStatement.setNull(3, Types.TIMESTAMP);
			else
				preparedStatement.setDate(3, java.sql.Date.valueOf(cmpt.getDiscontinued()));
			if (cmpt.getCompanyid() == 0)
				preparedStatement.setNull(4, Types.INTEGER);
			else
				preparedStatement.setInt(4, cmpt.getCompanyid());
			System.out.println("Try to exec : " + preparedStatement.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return findIdByName(cmpt.getName());
	}

	@Override
	public boolean delete(Computer cmpt) {
		String sql;
		sql = "DELETE FROM computer WHERE id = " + cmpt.getId();
		PersistenceManager.getInstance().sendToExec(sql);
		return false;
	}

	// Careful with the computer's id
	@Override
	public boolean update(Computer cmpt) {
		try {
			PreparedStatement preparedStatement = PersistenceManager.getInstance().getConn()
					.prepareStatement("UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");

			preparedStatement.setString(1, cmpt.getName());
			if (cmpt.getIntroduced() == null)
				preparedStatement.setNull(2, Types.TIMESTAMP);
			else
				preparedStatement.setDate(2, java.sql.Date.valueOf(cmpt.getIntroduced()));
			if (cmpt.getDiscontinued() == null)
				preparedStatement.setNull(3, Types.TIMESTAMP);
			else
				preparedStatement.setDate(3, java.sql.Date.valueOf(cmpt.getDiscontinued()));
			if (cmpt.getCompanyid() == 0)
				preparedStatement.setNull(4, Types.INTEGER);
			else
				preparedStatement.setInt(4, cmpt.getCompanyid());
			preparedStatement.setInt(5, cmpt.getId());
			System.out.println("Try to exec : " + preparedStatement.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
