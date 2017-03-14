package com.formation.cdb.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.formation.cdb.persistence.PersistenceManager;

public class CompanyDao extends Dao<Company> {
	public CompanyDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Company company) {
		String sql;
		sql = "INSERT INTO company(name) VALUES ('" + company.getName() + "')";
		PersistenceManager.getInstance().sendToExec(sql);
		System.out.println("Try to exec : " + sql);
		return findIdByName(company.getName());
	}

	@Override
	public boolean delete(Company company) {
		String sql;
		sql = "DELETE FROM company WHERE id = " + company.getId();
		return PersistenceManager.getInstance().sendToExec(sql);
	}

	@Override
	public boolean update(Company company) {
		String sql;
		sql = "UPDATE company SET name = '" + company.getName() + "' WHERE id = " + company.getId();
		return PersistenceManager.getInstance().sendToExec(sql);
	}

	@Override
	public Company find(int id) {
		Company company = new Company();

		try {
			String sql;
			sql = "SELECT * FROM company WHERE id = " + id;
			ResultSet rs = PersistenceManager.getInstance().sendQuery(sql);
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

	public Company findByName(String name) {
		Company company = new Company();
		String sql;
		sql = "SELECT * FROM company WHERE name = '" + name + "'";
		ResultSet rs = PersistenceManager.getInstance().sendQuery(sql);

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

	public int findIdByName(String name) {
		String sql;
		int id = 0;
		sql = "SELECT * FROM company WHERE name = '" + name + "'";
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
}
