package com.formation.cdb.model;

import java.sql.Date;
import java.time.LocalDate;

import com.formation.cdb.persistence.PersistenceManager;

public class Company {

	private int id = 0;
	private String name = null;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	public static void main(String[] args) {
		PersistenceManager.getInstance().connectToDb();

		Company company = new Company();
		company.setName("boitetropbien");
		System.out.println(company.toString());
		CompanyDao companyDao = new CompanyDao(PersistenceManager.getInstance().getConn());
		Company company2 = companyDao.find(1);
		System.out.println(company2.toString());

		// test create
		company2.setId(companyDao.create(company));
		company2 = companyDao.findByName(company.getName());
		System.out.println("Try to create : " + company.toString());
		System.out.println("Try to find " + company.getName() + " : " + company2);

		// try to update
		company2.setName("boitetropbien2");
		companyDao.update(company2);
		System.out.println("Try to update : \n" + company + "\n to \n" + company2);
		System.out.println("Find : " + companyDao.findByName(company2.getName()));

		// try to delete

		companyDao.delete(company2);
		System.out.println("Try to delete " + company2 + " : " + companyDao.findByName(company2.getName()));

		PersistenceManager.getInstance().close();

	}

}
