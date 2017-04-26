package com.formation.cdb.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.formation.cdb.model.Company;

@Repository("companyRepository")
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
