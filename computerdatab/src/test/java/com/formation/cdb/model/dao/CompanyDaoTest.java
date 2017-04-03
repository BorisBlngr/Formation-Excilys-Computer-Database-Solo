package com.formation.cdb.model.dao;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;

public class CompanyDaoTest {

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

    /**
     * Test to delete a Company and all its Computers.
     */
    @Test
    public void deleteCompanyAndComputersValid() {
        Company company1 = new Company.CompanyBuilder().name("company1").build();
        company1.setId(CompanyDao.INSTANCE.create(company1));

        Computer computer1 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer1")
                .company(CompanyDao.INSTANCE.find(company1.getId())).build();
        Computer computer2 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer2")
                .company(CompanyDao.INSTANCE.find(company1.getId())).build();
        Computer computer3 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer3")
                .company(CompanyDao.INSTANCE.find(company1.getId())).build();
        computer1.setId(ComputerDao.INSTANCE.create(computer1));
        computer2.setId(ComputerDao.INSTANCE.create(computer2));
        computer3.setId(ComputerDao.INSTANCE.create(computer3));

        CompanyDao.INSTANCE.delete(company1.getId());

        computer1 = ComputerDao.INSTANCE.findByName("computer1");
        computer2 = ComputerDao.INSTANCE.findByName("computer2");
        computer3 = ComputerDao.INSTANCE.findByName("computer3");
        company1 = CompanyDao.INSTANCE.findByName("company1");

        Assert.assertTrue(computer1.equals(nullComputer));
        Assert.assertTrue(computer2.equals(nullComputer));
        Assert.assertTrue(computer3.equals(nullComputer));
        Assert.assertTrue(company1.equals(nullCompany));

    }

}
