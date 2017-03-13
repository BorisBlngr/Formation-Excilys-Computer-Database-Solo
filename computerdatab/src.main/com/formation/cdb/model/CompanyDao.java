package com.formation.cdb.model;

import java.sql.Connection;

public class CompanyDao extends Dao<Company> {
	public CompanyDao(Connection conn) {
		super(conn);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int create(Company obj) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean delete(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Company find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
