package com.formation.cdb.model.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Company;

public class CompanyDaoTest {

    Company company = null;

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
        Company companyFound = CompanyDao.INSTANCE.find(1);
        Assert.assertTrue(companyFound.toString().equals("Company [id=1, name=Apple Inc.]"));
    }

    /**
     * Test create.
     */
    @Test
    public void createValid() {
        company.setId(CompanyDao.INSTANCE.create(company));
        Company companyFound = CompanyDao.INSTANCE.find(company.getId());
        // System.out.println(companyFound);
        // System.out.println(company);
        Assert.assertTrue(companyFound.toString().equals(company.toString()));
        CompanyDao.INSTANCE.delete(company);
    }

    /**
     * Test update.
     */
    @Test
    public void updateValid() {
        company.setId(CompanyDao.INSTANCE.create(company));
        company.setName("boitetropbien2");
        CompanyDao.INSTANCE.update(company);
        Company companyFound = CompanyDao.INSTANCE.find(company.getId());
        // System.out.println(companyFound.toString());
        // System.out.println(company.toString());
        Assert.assertTrue(companyFound.toString().equals(company.toString()));
        CompanyDao.INSTANCE.delete(company);
    }

    /**
     * Test delete.
     */
    @Test
    public void deleteValid() {
        Company nullCompany = new Company();
        Company companyFound = CompanyDao.INSTANCE.findByName("boitetropbien");
        // System.out.println(nullCompany.toString());
        // System.out.println(companyFound.toString());
        Assert.assertTrue(companyFound.toString().equals(nullCompany.toString()));
    }

}
