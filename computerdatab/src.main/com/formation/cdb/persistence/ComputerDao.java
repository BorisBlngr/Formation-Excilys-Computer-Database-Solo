package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Computer;

public enum ComputerDao implements Dao<Computer> {
	INSTANCE;
	final Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	//protected Connection connect = null;

	private ComputerDao() {
	}

	/**
	 * Methode pour trouver un computer en base en fonction de son id, renvoit
	 * le premier resultat.
	 * 
	 * @param id
	 *            L'id du computer à trouver.
	 * @return computer
	 */
	@Override
	public Computer find(int id) {
		Computer computer = new Computer();

		try {
			String sql;
			sql = "SELECT * FROM computer WHERE id = " + id;
			ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);
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

	/**
	 * Methode pour trouver un computer en fonction de son nom.
	 * 
	 * @param name
	 *            Le nom du computer à trouver.
	 * @return computer
	 */
	public Computer findByName(String name) {
		Computer computer = new Computer();
		String sql;
		sql = "SELECT * FROM computer WHERE name = '" + name + "'";
		ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);

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

	/**
	 * Methode pour avoir une liste de tous les computer en base.
	 * 
	 * @return computerList
	 */
	public List<Computer> findAll() {
		List<Computer> computerList = new ArrayList<Computer>();
		Computer computer;
		String sql = "SELECT * FROM computer";
		ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);

		try {
			while (rs.next()) {
				computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setCompanyId(rs.getInt("company_id"));
				if (rs.getDate("introduced") != null)
					computer.setIntroduced(rs.getDate("introduced").toLocalDate());
				if (rs.getDate("discontinued") != null) {
					computer.setDiscontinued(rs.getDate("discontinued").toLocalDate());
				}
				computerList.add(computer);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return computerList;
	}

	/**
	 * Methode pour trouver l'id d'un computer en fonction de son nom. renvoit
	 * le premier resultat.
	 * 
	 * @param name
	 *            Le nom du computer à trouver.
	 * @return id
	 */
	public int findIdByName(String name) {
		String sql;
		int id = 0;
		sql = "SELECT * FROM computer WHERE name = '" + name + "'";
		ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);

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

	/**
	 * Methode pour creer un nouveau computer en base, renvoit l'id de la ligne
	 * dans la bdd.
	 * 
	 * @param computer
	 *            Le computer à créer.
	 * @return id
	 */
	@Override
	public int create(Computer cmpt) {
		// String sql;
		// java.sql.Date.valueOf(cmpt.getDiscontinued())
		// sql = "INSERT INTO computer(name,introduced,discontinued,company_id)
		// VALUES ('" + cmpt.getName() + "', '"
		// + java.sql.Date.valueOf(cmpt.getIntroduced()) + "','"
		// + java.sql.Date.valueOf(cmpt.getDiscontinued()) + "','" +
		// cmpt.getCompanyid() + "')";
		try {
			PreparedStatement preparedStatement = PersistenceManager.INSTANCE.getConn()
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
			logger.info("Try to exec : " + preparedStatement.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return findIdByName(cmpt.getName());
	}

	/**
	 * Methode pour delete un computer en fonction de son id. renvoit le result
	 * de sendToExec.
	 * 
	 * @param computer
	 *            Le computer à delete.
	 * @return result
	 */
	@Override
	public boolean delete(Computer cmpt) {
		String sql;
		sql = "DELETE FROM computer WHERE id = " + cmpt.getId();
		return PersistenceManager.INSTANCE.sendToExec(sql);
	}

	/**
	 * Methode pour delete un computer en fonction de son id. renvoit le result
	 * de sendToExec
	 * 
	 * @param id
	 *            L'id du computer à delete
	 * @return result
	 */
	public boolean delete(int id) {
		String sql;
		sql = "DELETE FROM computer WHERE id = " + id;
		return PersistenceManager.INSTANCE.sendToExec(sql);
	}

	/**
	 * Methode pour update un computer en fonction de son id. Renvoit le result
	 * de sendToExec. Attention avec l'id.
	 * 
	 * @param computer
	 *            Le computer à update.
	 * @return result
	 */
	// Careful with the computer's id
	@Override
	public boolean update(Computer cmpt) {
		try {
			PreparedStatement preparedStatement = PersistenceManager.INSTANCE.getConn().prepareStatement(
					"UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?");

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
			logger.info("Try to exec : " + preparedStatement.toString());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
