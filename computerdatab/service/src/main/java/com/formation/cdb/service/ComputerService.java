package com.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.formation.cdb.mapper.ComputerMapper;
import com.formation.cdb.model.Computer;
import com.formation.cdb.model.dto.ComputerDto;
import com.formation.cdb.persistence.repository.ComputerRepository;
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
    ComputerRepository computerRepository;

    /**
     * Find Computer.
     * @param id Computer id.
     * @return computerDto
     */
    public ComputerDto findComputerDto(long id) {
        return ComputerMapper.toDto(computerRepository.findOne(id));
    }

    /**
     * Find a list of computer with index et maxInPage.
     * @param index Index.
     * @param maxInPage Number max in the list.
     * @return computerDtoList
     */
    public List<ComputerDto> findComputersInRange(int index, int maxInPage) {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        PageRequest request = new PageRequest(index - 1, maxInPage, Sort.Direction.ASC, "name");
        for (Computer computer : computerRepository.findAll(request).getContent()) {
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
        PageRequest request;
        String filterByStr;
        if (filterBy.equals(Search.COMPANIES)) {
            filterByStr = "company.name";
        } else {
            filterByStr = "name";
        }
        if (order.equals(Order.DESC)) {
            request = new PageRequest(index - 1, maxInPage, Sort.Direction.DESC, filterByStr);
        } else {
            request = new PageRequest(index - 1, maxInPage, Sort.Direction.ASC, filterByStr);
        }
        for (Computer computer : computerRepository.findByNameStartingWith(search, request).getContent()) {
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
        PageRequest request;
        String filterByStr;
        if (filterBy.equals(Search.COMPANIES)) {
            filterByStr = "company.name";
        } else {
            filterByStr = "name";
        }
        if (order.equals(Order.DESC)) {
            request = new PageRequest(index - 1, maxInPage, Sort.Direction.DESC, filterByStr);
        } else {
            request = new PageRequest(index - 1, maxInPage, Sort.Direction.ASC, filterByStr);
        }
        for (Computer computer : computerRepository.findByCompanyNameStartingWith(search, request).getContent()) {
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
        return ComputerMapper.toDto(computerRepository.findOne(id));
    }

    /**
     * Find all Computers.
     * @return computerDtoList
     */
    @Deprecated
    public List<ComputerDto> findAllComputer() {
        List<ComputerDto> computerDtoList = new ArrayList<ComputerDto>();
        for (Computer computer : computerRepository.findAll()) {
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
        System.out.println(computerDto);
        long id = computerRepository.save(ComputerMapper.toEntity(computerDto)).getId();
        dataInfo.increaseComputerCount();
        return id;
    }

    /**
     * Update a computer in base.
     * @param computerDto ComputerDto to update.
     * @return result
     */
    public boolean updateComputer(ComputerDto computerDto) {
        // TODO return false if it does nothing
        computerRepository.save(ComputerMapper.toEntity(computerDto));
        return true;
    }

    /**
     * Create or Update id the id is set or not.
     * @param computerDto cmpt
     * @return id
     */
    public long saveComputer(ComputerDto computerDto) {
        return computerRepository.save(ComputerMapper.toEntity(computerDto)).getId();
    }

    /**
     * Delete a computer in base.
     * @param id Computer id.
     * @return idComputer
     */
    public boolean deleteComputer(Long id) {
        computerRepository.delete(id);
        dataInfo.decreaseComputerCount();
        return true;
    }

    /**
     * Get number of computers in the db. The value saved in DataInfo. Faster
     * @return count
     */
    public int getNbComputers() {
        return dataInfo.getComputerCount();
    }

    /**
     * Get number of computers in the db. Costly request.
     * @return count
     */
    public int getNbComputersDb() {
        return Math.toIntExact(computerRepository.count());
    }

    /**
     * Get the number of element with a name like search.
     * @param search String to search.
     * @return count
     */
    public int getNbComputersSearchName(String search) {
        return computerRepository.countByNameStartingWith(search);
    }

    /**
     * Get the number of element with a company name like search.
     * @param search String to search.
     * @return count
     */
    public int getNbComputersSearchCompanyName(String search) {
        return computerRepository.countByCompanyNameStartingWith(search);
    }

    /**
     * Main.
     * @param args Args.
     */
    public static void main(String[] args) {
    }
}
