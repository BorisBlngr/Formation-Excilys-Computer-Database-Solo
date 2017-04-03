package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;

import com.formation.cdb.mapper.CompanyMapper;
import com.formation.cdb.model.Company;
import com.formation.cdb.model.dao.CompanyDao;
import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.util.Order;

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
            computerDtoList.add(CompanyMapper.INSTANCE.toDto(company));
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
            companyDtoList.add(CompanyMapper.INSTANCE.toDto(company));
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
            companyDtoList.add(CompanyMapper.INSTANCE.toDto(company));
        }
        return companyDtoList;
    }

    public int getNbCompanies() {
        return CompanyDao.INSTANCE.getRow();
    }

}
