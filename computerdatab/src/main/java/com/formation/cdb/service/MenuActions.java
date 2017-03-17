package com.formation.cdb.service;

import java.util.List;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.CompanyDao;
import com.formation.cdb.persistence.ComputerDao;
import com.formation.cdb.ui.ComputerUi;

public enum MenuActions {
    INSTANCE;

    /**
     * Construteur.
     * */
    MenuActions() {
    }
    /**
     * Find Computer.
     * @param id Computer id.
     * @return computer
     * */
    public Computer findComputer(long id) {
        return ComputerDao.INSTANCE.find(id);
    }
    /**
     * Find a list of computer with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return computerList
     * */
    public List<Computer> findComputersInRange(int index, int maxInPage) {
        return ComputerDao.INSTANCE.findInRange(index, maxInPage);
    }
    /**
     * Find ComputerUi.
     * @param id Computer id.
     * @return computerUi
     * */
    public ComputerUi findComputerUi(long id) {
        return ComputerDao.INSTANCE.findUi(id);
    }
    /**
     * Find all Computers.
     * @return computerList
     * */
    public List<Computer> findAllComputer() {
        return ComputerDao.INSTANCE.findAll();
    }
    /**
     * Find a list of company with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return companyList
     * */
    public List<Company> findCompaniesInRange(int index, int maxInPage) {
        return CompanyDao.INSTANCE.findInRange(index, maxInPage);
    }
    /**
     * Find all companies.
     * @return companyList
     * */
    public List<Company> findAllCompany() {
        return CompanyDao.INSTANCE.findAll();
    }
    /**
     * Create a computer in base.
     * @param computer Computer to create.
     * @return idComputer
     * */
    public long createComputer(Computer computer) {
        return ComputerDao.INSTANCE.create(computer);
    }
    /**
     * Update a computer in base.
     * @param computer Computer to update.
     * @return result
     * */
    public boolean updateComputer(Computer computer) {
        return ComputerDao.INSTANCE.update(computer);
    }
    /**
     * Delete a computer in base.
     * @param id Computer id.
     * @return idComputer
     * */
    public boolean deleteComputer(Long id) {
        return ComputerDao.INSTANCE.delete(id);
    }

    public int getNbComputers() {
        return ComputerDao.INSTANCE.getRow();
    }

    public int getNbCompanies() {
        return CompanyDao.INSTANCE.getRow();
    }
    /**
     * Main.
     * @param args Args.
     * */
    public static void main(String[] args) {
    }
}
