package com.formation.cdb.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import com.formation.cdb.persistence.PersistenceManager;

public class Computer {
	private int id = 0;
	private String name;
	private LocalDate introduced = null;
	private int companyId = 0;
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

	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public int getCompanyid() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
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
		PersistenceManager.getInstance().connectToDb();

		LocalDate introduced = LocalDate.of(1980, 10, 10);
		LocalDate discontinued = LocalDate.of(1990, 10, 10);
		Date introducedD = Date.valueOf(introduced);
		Date discontinuedD = Date.valueOf(discontinued);

		Computer computer = new Computer();
		computer.setCompanyId(2);
		computer.setName("Orditropbien");
		computer.setIntroduced(introducedD.toLocalDate());
		computer.setDiscontinued(discontinuedD.toLocalDate());
		System.out.println(computer.toString());
		System.out.println(java.sql.Date.valueOf(computer.getIntroduced()));
		ComputerDao computerDao = new ComputerDao(PersistenceManager.getInstance().getConn());
		Computer computer2 = computerDao.find(600);
		System.out.println(computer2.toString());

		// test create
		computer.setId(computerDao.create(computer));
		computer2 = computerDao.findByName(computer.getName());
		System.out.println("Try to create : " + computer.toString());
		System.out.println("Try to find " + computer.getName() + " : " + computer2);

		// try to update
		computer.setCompanyId(8);
		computerDao.update(computer);
		System.out.println("Try to update : \n" + computer2 + "\n to \n" + computer);
		System.out.println("Find : " + computerDao.findByName(computer.getName()));

		// try to delete

		computerDao.delete(computer);
		System.out.println("Try to delete " + computer + " : " + computerDao.findByName(computer.getName()));
		
		//try find all
		List<Computer> computerList = computerDao.findAll();
		System.out.println(computerList.size());
		
		PersistenceManager.getInstance().close();
		
		
	}
}