package com.formation.cdb.service;

import java.util.List;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;
import com.formation.cdb.model.dao.CompanyDao;
import com.formation.cdb.model.dao.ComputerDao;
import com.formation.cdb.model.dto.ComputerDto;

public enum ComputerService {
    INSTANCE;

    /**
     * Construteur.
     */
    ComputerService() {
    }

    /**
     * Find Computer.
     * @param id Computer id.
     * @return computer
     */
    public ComputerDto findComputerDto(long id) {
        return ComputerDao.INSTANCE.find(id);
    }

    /**
     * Find a list of computer with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return computerList
     */
    public List<ComputerDto> findComputersInRange(int index, int maxInPage) {
        return ComputerDao.INSTANCE.findInRange(index, maxInPage);
    }

    /**
     * Find ComputerUi.
     * @param id Computer id.
     * @return computerUi
     */
    public Computer findComputer(long id) {
        return ComputerDao.INSTANCE.findComputer(id);
    }

    /**
     * Find all Computers.
     * @return computerList
     */
    public List<ComputerDto> findAllComputer() {
        return ComputerDao.INSTANCE.findAll();
    }

    /**
     * Find a list of company with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return companyList
     */
    public List<Company> findCompaniesInRange(int index, int maxInPage) {
        return CompanyDao.INSTANCE.findInRange(index, maxInPage);
    }

    /**
     * Find all companies.
     * @return companyList
     */
    public List<Company> findAllCompany() {
        return CompanyDao.INSTANCE.findAll();
    }

    /**
     * Create a computer in base.
     * @param computerDto ComputerDto to create.
     * @return idComputer
     */
    public long createComputer(ComputerDto computerDto) {
        return ComputerDao.INSTANCE.create(computerDto);
    }

    /**
     * Update a computer in base.
     * @param computerDto ComputerDto to update.
     * @return result
     */
    public boolean updateComputer(ComputerDto computerDto) {
        return ComputerDao.INSTANCE.update(computerDto);
    }

    /**
     * Delete a computer in base.
     * @param id Computer id.
     * @return idComputer
     */
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
     */
    public static void main(String[] args) {
    }
}
