package com.formation.cdb.service;

import java.util.List;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.CompanyDao;
import com.formation.cdb.persistence.ComputerDao;
import com.formation.cdb.ui.ComputerUi;

public enum MenuActions {
	INSTANCE;

	private MenuActions() {

	}

	public List<Computer> listComputersAction() {
		return null;

	}

	public Computer findComputer(long id) {
		return ComputerDao.INSTANCE.find(id);
	}
	public List<Computer> findComputersInRange(int index, int maxInPage){
		return ComputerDao.INSTANCE.findInRange(index, maxInPage);
	}
	public ComputerUi findComputerUi(long id) {
		return ComputerDao.INSTANCE.findUi(id);
	}

	public List<Computer> findAllComputer() {
		return ComputerDao.INSTANCE.findAll();
	}

	public List<Company> findAllCompany() {
		return CompanyDao.INSTANCE.findAll();
	}

	public long createComputer(Computer computer) {
		return ComputerDao.INSTANCE.create(computer);
	}

	public boolean updateComputer(Computer computer) {
		return ComputerDao.INSTANCE.update(computer);
	}

	public boolean deleteComputer(Long id) {
		return ComputerDao.INSTANCE.delete(id);
	}

	public static void main(String[] args) {
	}
}
