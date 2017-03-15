package com.formation.cdb.model;

import java.util.List;

import com.formation.cdb.persistence.CompanyDao;

public class Company {

	private long id = 0;
	private String name = null;

	public Company() {
	}

	public Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(long id) {
		this.id = id;
	}

	public static class CompanyBuilder {
		String name;
		long id;

		public CompanyBuilder() {
		}

		public CompanyBuilder id(long id) {
			this.id = id;
			return this;
		}

		public CompanyBuilder name(String name) {
			this.name = name;
			return this;
		}

		public Company build() {
			return new Company(this);
		}

	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public static void main(String[] args) {

		Company company = new Company.CompanyBuilder().name("boitetropbien").build();
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
