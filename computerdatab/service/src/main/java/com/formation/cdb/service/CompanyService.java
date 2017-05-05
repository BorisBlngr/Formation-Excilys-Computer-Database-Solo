package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.formation.cdb.mapper.CompanyMapper;
import com.formation.cdb.model.Company;
import com.formation.cdb.model.dto.CompanyDto;
import com.formation.cdb.persistence.repository.CompanyRepository;
import com.formation.cdb.util.Order;

/**
 * @author excilys
 *
 */
@Service("companyService")
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

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
        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        if (index < 1) {
            return companyDtoList;
        }
        PageRequest request;
        if (order.equals(Order.DESC)) {
            request = new PageRequest(index - 1, maxInPage, Sort.Direction.DESC, "name");
        } else {
            request = new PageRequest(index - 1, maxInPage, Sort.Direction.ASC, "name");
        }
        for (Company company : companyRepository.findByNameStartingWith(search, request).getContent()) {
            companyDtoList.add(CompanyMapper.toDto(company));
        }
        return companyDtoList;
    }

    /**
     * @param id id
     * @return companyDto
     */
    public CompanyDto findOne(long id) {
        return CompanyMapper.toDto(companyRepository.findOne(id));
    }

    /**
     * Find a list of company with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return companyDtoList
     */
    public List<CompanyDto> findCompaniesInRange(int index, int maxInPage) {
        List<CompanyDto> companyDtoList = new ArrayList<CompanyDto>();
        if (index < 1) {
            return companyDtoList;
        }
        PageRequest request = new PageRequest(index - 1, maxInPage, Sort.Direction.ASC, "name");
        for (Company company : companyRepository.findAll(request).getContent()) {
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
        for (Company company : companyRepository.findAll()) {
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
        return companyRepository.save(CompanyMapper.toEntity(companyDto)).getId();
    }

    /**
     * Method to delete all the computers of a Company and the Company.
     * @param id Id of the Company.
     * @return result
     */
    public boolean delete(long id) {
        // TODO return false if it does nothing
        companyRepository.delete(id);
        return true;
    }

    public int getNbCompanies() {
        return Math.toIntExact(companyRepository.count());
    }
}
