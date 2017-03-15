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

	public static void main(String[] args) {

		LocalDate introduced = LocalDate.of(1980, 10, 10);
		LocalDate discontinued = LocalDate.of(1990, 10, 10);

		Computer computer = new Computer();
		computer.setCompanyId(2);
		computer.setName("Orditropbien");
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
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
		
		//try find all
		List<Computer> computerList = ComputerDao.INSTANCE.findAll();
		System.out.println(computerList.size());
		
		
		
	}
}