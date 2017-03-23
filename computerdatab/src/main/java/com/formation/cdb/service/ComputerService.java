package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.mapper.ComputerMapper;
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
        return ComputerMapper.INSTANCE.toDto(ComputerDao.INSTANCE.find(id));
    }

    /**
     * Find a list of computer with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return computerList
     */
    public List<ComputerDto> findComputersInRange(int index, int maxInPage) {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : ComputerDao.INSTANCE.findInRange(index, maxInPage)) {
            computerDtoList.add(ComputerMapper.INSTANCE.toDto(computer));
        }
        return computerDtoList;
    }

    /**
     * Find All Computer.
     * @param id Computer id.
     * @return computer
     */
    @Deprecated
    public ComputerDto findComputer(long id) {
        return ComputerMapper.INSTANCE.toDto(ComputerDao.INSTANCE.findComputer(id));
    }

    /**
     * Find all Computers.
     * @return computerList
     */
    @Deprecated
    public List<ComputerDto> findAllComputer() {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : ComputerDao.INSTANCE.findAll()) {
            computerDtoList.add(ComputerMapper.INSTANCE.toDto(computer));
        }
        return computerDtoList;
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
    @Deprecated
    public List<Company> findAllCompany() {
        return CompanyDao.INSTANCE.findAll();
    }

    /**
     * Create a computer in base.
     * @param computerDto ComputerDto to create.
     * @return idComputer
     */
    public long createComputer(ComputerDto computerDto) {
        return ComputerDao.INSTANCE.create(ComputerMapper.INSTANCE.toEntity(computerDto));
    }

    /**
     * Update a computer in base.
     * @param computerDto ComputerDto to update.
     * @return result
     */
    public boolean updateComputer(ComputerDto computerDto) {
        return ComputerDao.INSTANCE.update(ComputerMapper.INSTANCE.toEntity(computerDto));
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
