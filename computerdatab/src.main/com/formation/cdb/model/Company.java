package com.formation.cdb.model;

import java.util.List;

import com.formation.cdb.persistence.CompanyDao;

public class Company {

	private long id = 0;
	private String name = null;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
		
		Company company = new Company();
		company.setName("boitetropbien");
		System.out.println(company.toString());
		Company company2 = CompanyDao.INSTANCE.find(1);
		System.out.println(company2.toString());

		// test create
		company2.setId(CompanyDao.INSTANCE.create(company));
		company2 = CompanyDao.INSTANCE.findByName(company.getName());
		System.out.println("Try to create : " + company.toString());
		System.out.println("Try to find " + company.getName() + " : " + company2);

		// try to update
		company2.setName("boitetropbien2");
		CompanyDao.INSTANCE.update(company2);
		System.out.println("Try to update : \n" + company + "\n to \n" + company2);
		System.out.println("Find : " + CompanyDao.INSTANCE.findByName(company2.getName()));

		// try to delete

		CompanyDao.INSTANCE.delete(company2);
		System.out.println("Try to delete " + company2 + " : " + CompanyDao.INSTANCE.findByName(company2.getName()));

		// try find all
		List<Company> companyList = CompanyDao.INSTANCE.findAll();
		System.out.println(companyList.size());


	}

}
