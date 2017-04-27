package com.formation.cdb.mapper;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.dto.CompanyDto;

public class CompanyMapperTest {
    Company company = null;
    CompanyDto companyDto = null;

    /**
     * Execute before Class.
     * @throws Exception Exceptions.
     */
    @BeforeClass
    public static void executerBeforeClass() throws Exception {
    }

    /**
     * Execute after Class.
     * @throws Exception Exceptions.
     */
    @AfterClass
    public static void executerAfterClass() throws Exception {
    }

    /**
     * Execute before test.
     * @throws Exception
     */
    @Before
    public void executerAvantChaqueTest() {
        companyDto = new CompanyDto.CompanyDtoBuilder().name("toto").id(36).build();
        company = new Company.CompanyBuilder().name("toto").id(36).build();
    }

    /**
     * Execute after test.
     * @throws Exception
     */
    @After
    public void executerApresChaqueTest() {
        company = null;
        companyDto = null;
    }

    /**
     * Test map to dto.
     */
    @Test
    public void entityToDtoValid() {
        Assert.assertTrue(CompanyMapper.toDto(company).equals(companyDto));
    }

    /**
     * Test map to entity.
     */
    @Test
    public void dtoToEntityValid() {
        Assert.assertTrue(CompanyMapper.toEntity(companyDto).equals(company));
    }
}
