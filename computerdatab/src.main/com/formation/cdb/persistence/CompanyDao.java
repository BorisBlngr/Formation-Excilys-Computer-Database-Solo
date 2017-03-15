package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.formation.cdb.model.Company;

public enum CompanyDao implements Dao<Company> {
	INSTANCE;
	final Logger logger = LoggerFactory.getLogger(CompanyDao.class);

	private CompanyDao() {
	}

	/**
	 * Methode pour creer une nouvelle company en base, renvoit l'id de la ligne
	 * dans la bdd.
	 * 
	 * @param company
	 *            La company à créer.
	 * @return id
	 */
	@Override
	public int create(Company company) {
		try {
			PreparedStatement preparedStatement = PersistenceManager.INSTANCE.getConn()
					.prepareStatement("INSERT INTO company(name) VALUES (?)");
			
			preparedStatement.setString(1, company.getName());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return findIdByName(company.getName());
	}

	/**
	 * Methode pour supprimer la company en base, renvoit le result de
	 * sendToExec.
	 * 
	 * @param company
	 *            La company à delete.
	 * @return result
	 */
	@Override
	public boolean delete(Company company) {
		String sql;
		sql = "DELETE FROM company WHERE id = " + company.getId();
		return PersistenceManager.INSTANCE.sendToExec(sql);
	}

	/**
	 * Methode pour mettre à jour une company.
	 * 
	 * @param company
	 *            La company à update.
	 * @return false
	 */
	@Override
	public boolean update(Company company) {
		try {
			PreparedStatement preparedStatement = PersistenceManager.INSTANCE.getConn()
					.prepareStatement("UPDATE company SET name = ? WHERE id = ? ");
			preparedStatement.setString(1, company.getName());
			preparedStatement.setInt(2, company.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Methode pour trouver une company en fonction de son id.
	 * 
	 * @param id
	 *            L'id de la company à trouver.
	 * @return company
	 */
	@Override
	public Company find(int id) {
		Company company = new Company();

		try {
			String sql;
			sql = "SELECT * FROM company WHERE id = " + id;
			ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);
			// Extract data from result set
			if (rs.first()) {
				company.setId(id);
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	/**
	 * Methode pour avoir une liste de toutes les companies en base. Renvoit
	 * sous forme d'arraylist.
	 * 
	 * @return companyList
	 */
	public List<Company> findAll() {
		List<Company> companyList = new ArrayList<Company>();
		try {
			String sql = "SELECT * FROM company";
			ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);
			Company company;
			while (rs.next()) {
				company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
				companyList.add(company);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companyList;
	}

	/**
	 * Methode pour trouver une company en fonction de son name en base.
	 * 
	 * @param name
	 *            Le nom de la company à trouver.
	 * @return company
	 */
	public Company findByName(String name) {
		Company company = new Company();
		String sql;
		sql = "SELECT * FROM company WHERE name = '" + name + "'";
		ResultSet rs = PersistenceManager.INSTANCE.sendQuery(sql);

		try {
			if (rs.first()) {
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return company;
	}

	/**
	 * Methode pour trouver l'id d'une company en fonction de son name en base.
	 * renvoit toujours le premier resultat.
	 * 
	 * @param name
	 *            Le nom de la company à trouver.
	 * @return id
	 */
	public int findIdByName(String name) {
		String sql;
		int id = 0;
		sql = "SELECT * FROM company WHERE name = '" + name + "'";
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
}
