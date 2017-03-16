package com.formation.cdb.model;

import java.time.LocalDate;
import java.util.List;

import com.formation.cdb.persistence.ComputerDao;

public class Computer {
	private long id = 0;
	private String name;
	private LocalDate introduced = null;
	private long companyId = 0;
	private LocalDate discontinued = null;

	public Computer() {
	}

	public Computer(int id, String name, LocalDate introduced, int company_id, LocalDate discontinued) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.companyId = company_id;
		this.discontinued = discontinued;

	}

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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public long getCompanyid() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", companyId=" + companyId
				+ ", discontinued=" + discontinued + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (companyId ^ (companyId >>> 32));
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (companyId != other.companyId)
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public Computer(ComputerBuilder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
	}

	public static class ComputerBuilder {
		String name;
		long id;
		LocalDate introduced = null;
		long companyId = 0;
		LocalDate discontinued = null;

		public ComputerBuilder() {
		}

		public ComputerBuilder id(long id) {
			this.id = id;
			return this;
		}

		public ComputerBuilder name(String name) {
			this.name = name;
			return this;
		}

		public ComputerBuilder introduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public ComputerBuilder discontinued(LocalDate discontinued) {
			this.introduced = discontinued;
			return this;
		}

		public ComputerBuilder companyId(long companyId) {
			this.companyId = companyId;
			return this;
		}

		public Computer build() {
			return new Computer(this);
		}

	}

	public static void main(String[] args) {

		Computer computer = new Computer.ComputerBuilder().companyId(2).name("Orditropbien")
				.introduced(LocalDate.of(1980, 10, 10)).discontinued(LocalDate.of(1990, 10, 10)).build();
		System.out.println(computer.toString());
		System.out.println(java.sql.Date.valueOf(computer.getIntroduced()));
		Computer computer2 = ComputerDao.INSTANCE.find(600);
		System.out.println(computer2.toString());

		// test create
		computer.setId(ComputerDao.INSTANCE.create(computer));
		computer2 = ComputerDao.INSTANCE.findByName(computer.getName());
		System.out.println("Try to create : " + computer.toString());
		System.out.println("Try to find " + computer.getName() + " : " + computer2);

		// try to update
		computer.setCompanyId(8);
		ComputerDao.INSTANCE.update(computer);
		System.out.println("Try to update : \n" + computer2 + "\n to \n" + computer);
		System.out.println("Find : " + ComputerDao.INSTANCE.findByName(computer.getName()));

		// try to delete

		ComputerDao.INSTANCE.delete(computer);
		System.out.println("Try to delete " + computer + " : " + ComputerDao.INSTANCE.findByName(computer.getName()));

		// try find all
		List<Computer> computerList = ComputerDao.INSTANCE.findAll();
		System.out.println(computerList.size());

	}
}