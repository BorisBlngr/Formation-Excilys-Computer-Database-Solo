package com.formation.cdb.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.formation.cdb.persistence.repository.CompanyRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-module.xml"})
public class CompanyRepositoryTest {
    @Autowired
    CompanyRepository companyRepository;

    Company company = null;
    Computer nullComputer = null;
    Company nullCompany = null;

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
        company = new Company();
        company.setName("boitetropbien");
        nullComputer = new Computer();
        nullCompany = new Company();
    }

    /**
     * Execute after test.
     * @throws Exception
     */
    @After
    public void executerApresChaqueTest() {
        company = null;
    }

    /**
     * Test find.
     */
    @Test
    public void findIsValid() {
        Company companyFound = companyRepository.findOne((long) 1);
        Assert.assertTrue(companyFound.toString().equals("Company [id=1, name=Apple Inc.]"));
    }

    /**
     * Test create.
     */
    @Test
    public void createValid() {
        company = companyRepository.save(company);
        Company companyFound = companyRepository.findOne(company.getId());
        // System.out.println(companyFound);
        // System.out.println(company);
        Assert.assertTrue(companyFound.toString().equals(company.toString()));
        companyRepository.delete(company);
    }

    /**
     * Test update.
     */
    @Test
    public void updateValid() {
        company = companyRepository.save(company);
        company.setName("boitetropbien2");
        companyRepository.save(company);
        Company companyFound = companyRepository.findOne(company.getId());
        // System.out.println(companyFound.toString());
        // System.out.println(company.toString());
        Assert.assertTrue(companyFound.toString().equals(company.toString()));
        companyRepository.delete(company);
    }

    /**
     * Test delete.
     */
    @Test
    public void deleteValid() {
        Company companyToDelete = new Company();
        companyToDelete.setName("boite5454");
        long id = companyRepository.save(companyToDelete).getId();
        companyRepository.delete(id);
        Company companyFound = companyRepository.findOne(id);
        // System.out.println(nullCompany.toString());
        // System.out.println(companyFound.toString());
        Assert.assertTrue(companyFound == null);
    }

}
