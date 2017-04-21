package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.formation.cdb.mapper.ComputerMapper;
import com.formation.cdb.model.Computer;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.persistence.dao.ComputerDao;
import com.formation.cdb.util.DataInfo;
import com.formation.cdb.util.Order;
import com.formation.cdb.util.Search;

/**
 * @author excilys
 *
 */
@Service("computerService")
public class ComputerService {
    @Autowired
    DataInfo dataInfo;
    @Autowired
    ComputerDao computerDao;

    /**
     * Find Computer.
     * @param id Computer id.
     * @return computerDto
     */
    public ComputerDto findComputerDto(long id) {
        return ComputerMapper.toDto(computerDao.find(id));
    }

    /**
     * Find a list of computer with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return computerDtoList
     */
    public List<ComputerDto> findComputersInRange(int index, int maxInPage) {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : computerDao.findInRange(index, maxInPage)) {
            computerDtoList.add(ComputerMapper.toDto(computer));
        }
        return computerDtoList;
    }

    /**
     * Find a list of computers, or page, with a specific string in their names
     * and with the order Order.DESC or Order.ASC.
     * @param index Index of the page.
     * @param maxInPage Max element in the list.
     * @param search String to search.
     * @param filterBy Sort the list by.
     * @param order ASC or DESC for the List.
     * @return computerDtoList
     */
    public List<ComputerDto> findComputersInRangeSearchName(int index, int maxInPage, String search, Search filterBy,
            Order order) {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : computerDao.findInRangeSearchName(index, maxInPage, search, filterBy, order)) {
            computerDtoList.add(ComputerMapper.toDto(computer));
        }
        return computerDtoList;
    }

    /**
     * Find a list of computers, or page, with a specific string in their
     * company's name and with the order Order.DESC or Order.ASC.
     * @param index Index of the page.
     * @param maxInPage Max element in the list.
     * @param search String to search.
     * @param filterBy Sort the list by.
     * @param order ASC or DESC for the List.
     * @return computerDtoList
     */
    public List<ComputerDto> findInRangeSearchCompanyName(int index, int maxInPage, String search, Search filterBy,
            Order order) {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : computerDao.findInRangeSearchCompanyName(index, maxInPage, search, filterBy, order)) {
            computerDtoList.add(ComputerMapper.toDto(computer));
        }
        return computerDtoList;
    }

    /**
     * Find All Computer.
     * @param id Computer id.
     * @return computerDto
     */
    @Deprecated
    public ComputerDto findComputer(long id) {
        return ComputerMapper.toDto(computerDao.find(id));
    }

    /**
     * Find all Computers.
     * @return computerDtoList
     */
    @Deprecated
    public List<ComputerDto> findAllComputer() {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : computerDao.findAll()) {
            computerDtoList.add(ComputerMapper.toDto(computer));
        }
        return computerDtoList;
    }

    /**
     * Create a computer in base.
     * @param computerDto ComputerDto to create.
     * @return idComputer
     */
    public long createComputer(ComputerDto computerDto) {
        return computerDao.create(ComputerMapper.toEntity(computerDto));
    }

    /**
     * Update a computer in base.
     * @param computerDto ComputerDto to update.
     * @return result
     */
    public boolean updateComputer(ComputerDto computerDto) {
        return computerDao.update(ComputerMapper.toEntity(computerDto));
    }

    /**
     * Delete a computer in base.
     * @param id Computer id.
     * @return idComputer
     */
    public boolean deleteComputer(Long id) {
        return computerDao.delete(id);
    }

    /**
     * Get number of computers in the db. The value saved in DataInfo. Faster
     * @return count
     */
    public int getNbComputers() {
        System.out.println(dataInfo.getComputerCount());
        return dataInfo.getComputerCount();
        // return computerDao.getRow();
    }

    /**
     * Get number of computers in the db. Costly request.
     * @return count
     */
    public int getNbComputersDb() {
        return computerDao.getRow();
    }

    /**
     * Get the number of element with a name like search.
     * @param search String to search.
     * @return count
     */
    public int getNbComputersSearchName(String search) {
        return computerDao.getRowSearchName(search);
    }

    /**
     * Get the number of element with a company name like search.
     * @param search String to search.
     * @return count
     */
    public int getNbComputersSearchCompanyName(String search) {
        return computerDao.getRowSearchCompanyName(search);
    }

    /**
     * Main.
     * @param args Args.
     */
    public static void main(String[] args) {
    }
}
