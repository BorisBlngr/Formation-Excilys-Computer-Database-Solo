package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.mapper.CompanyMapper;
import com.formation.cdb.model.Company;
import com.formation.cdb.model.dao.CompanyDao;
import com.formation.cdb.model.dao.ComputerDao;
import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.util.Order;

/**
 * @author excilys
 *
 */
public enum CompanyService {
    INSTANCE;

    /**
     * Find a list of companies, or page, with a specific string in their names
     * and with the order Order.DESC or Order.ASC.
     * @param index Index of the page.
     * @param maxInPage Max element in the list.
     * @param search String to search.
     * @param order ASC or DESC for the List.
     * @return companyDtoList
     */
    public List<CompanyDto> findCompaniesInRangeSearchName(int index, int maxInPage, String search, Order order) {
        List<CompanyDto> computerDtoList = new ArrayList<CompanyDto>();
        for (Company company : CompanyDao.INSTANCE.findInRangeSearchName(index, maxInPage, search, order)) {
            computerDtoList.add(CompanyMapper.toDto(company));
        }
        return computerDtoList;
    }

    /**
     * Find a list of company with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return companyDtoList
     */
    public List<CompanyDto> findCompaniesInRange(int index, int maxInPage) {
        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        for (Company company : CompanyDao.INSTANCE.findInRange(index, maxInPage)) {
            companyDtoList.add(CompanyMapper.toDto(company));
        }
        return companyDtoList;
    }

    /**
     * Find all companies.
     * @return companyDtoList
     */
    @Deprecated
    public List<CompanyDto> findAllCompany() {
        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        for (Company company : CompanyDao.INSTANCE.findAll()) {
            companyDtoList.add(CompanyMapper.toDto(company));
        }
        return companyDtoList;
    }

    /**
     * Create a Company with a CompanyDto and return its id.
     * @param companyDto CompanyDto.
     * @return id
     */
    public long create(CompanyDto companyDto) {
        return CompanyDao.INSTANCE.create(CompanyMapper.toEntity(companyDto));
    }

    /**
     * Method to delete all the computers of a Company and the Company.
     * @param id Id of the Company.
     * @return result
     */
    public boolean delete(long id) {
        return ComputerDao.INSTANCE.deleteWithCompanyId(id) && CompanyDao.INSTANCE.delete(id);
    }

    public int getNbCompanies() {
        return CompanyDao.INSTANCE.getRow();
    }
}
