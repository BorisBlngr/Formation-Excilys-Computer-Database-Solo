package com.formation.cdb.mapper;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.dto.CompanyDto;

/**
 * CompanyDto => Company Company => CompanyDto.
 */
public class CompanyMapper {

    /**
     * Transforme un Company en CompanyDto.
     * @param company Company.
     * @return companyDto
     */
    public static CompanyDto toDto(Company company) {
        CompanyDto companyDto = new CompanyDto.CompanyDtoBuilder().id(company.getId()).name(company.getName()).build();
        return companyDto;
    }

    /**
     * Transforme un CompanyDto en Company.
     * @param companyDto CompanyDto.
     * @return Company
     */
    public static Company toEntity(CompanyDto companyDto) {
        Company company = new Company.CompanyBuilder().id(companyDto.getId()).name(companyDto.getName()).build();
        return company;
    }
}
