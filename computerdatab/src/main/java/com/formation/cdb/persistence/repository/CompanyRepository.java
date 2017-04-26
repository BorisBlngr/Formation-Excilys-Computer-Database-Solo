package com.formation.cdb.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formation.cdb.model.Company;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /**
     * @param name name
     * @return companyList
     */
    List<Company> findByNameLike(String name);
}
