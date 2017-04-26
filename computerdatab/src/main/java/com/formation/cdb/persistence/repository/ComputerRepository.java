package com.formation.cdb.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formation.cdb.model.Computer;

@Repository("computerRepository")
public interface ComputerRepository extends JpaRepository<Computer, Long> {

    /**
     * @param name name
     * @param pageable page request
     * @return computerList
     */
    Page<Computer> findByNameStartingWith(String name, Pageable pageable);

    /**
     * @param name name
     * @param pageable page request
     * @return computerList
     */
    Page<Computer> findByCompanyNameStartingWith(String name, Pageable pageable);

    /**
     * @param name name
     * @return count
     */
    int countByNameStartingWith(String name);

    /**
     * @param name name
     * @return count
     */
    int countByCompanyNameStartingWith(String name);
}
