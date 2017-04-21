package com.formation.cdb.persistence.dao;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.Computer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-module.xml")
public class ComputerDaoTest {
    @Autowired
    CompanyDao companyDao;
    @Autowired
    ComputerDao computerDao;

    Computer computer = null;
    Computer nullComputer = null;

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
     */
    @Before
    public void executerAvantChaqueTest() {
        computer = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("test nom ordi random")
                .company(new Company.CompanyBuilder().name("Thinking Machines").id(2).build()).build();
        nullComputer = new Computer();
    }

    /**
     * Execute after test.
     */
    @After
    public void executerApresChaqueTest() {
        computer = null;
    }

    /**
     * Test Find.
     */
    @Test
    public void findIsValid() {
        Computer computerFound = computerDao.find(1);
        // System.out.println(computerFound);
        Assert.assertTrue(computerFound.toString().equals(
                "Computer [id=1, name=MacBook Pro 15.4 inch, introduced=null, discontinued=null, company=Company [id=1, name=Apple Inc.]]"));
    }

    /**
     * Test create.
     */
    @Test
    public void createValid() {
        Computer newComputer = new Computer.ComputerBuilder().name("Test unitaire create name")
                .introduced(LocalDate.of(1990, 10, 10)).discontinued(LocalDate.of(2000, 10, 10))
                .company(new Company.CompanyBuilder().id(2).name("Thinking Machines").build()).build();
        newComputer.setId(computerDao.create(newComputer));
        Computer computerFound = computerDao.find(newComputer.getId());
        System.out.println(newComputer);
        System.out.println(computerFound);
        Assert.assertTrue(computerFound.toString().equals(newComputer.toString()));
        computerDao.delete(newComputer);
    }

    /**
     * Test Update.
     */
    @Test
    public void updateValid() {
        Computer newComputer = new Computer.ComputerBuilder().name("Test unitaire create name")
                .introduced(LocalDate.of(1990, 10, 10)).discontinued(LocalDate.of(2000, 10, 10))
                .company(new Company.CompanyBuilder().id(2).name("Thinking Machines").build()).build();
        newComputer.setId(computerDao.create(newComputer));
        newComputer.setName("Test unitaire create name2");
        computerDao.update(newComputer);
        Computer computerFound = computerDao.find(newComputer.getId());
        // System.out.println(computerFound.toString());
        // System.out.println(newComputer.toString());
        Assert.assertTrue(computerFound.equals(newComputer));
        computerDao.delete(newComputer);
    }

    /**
     * Test delete.
     */
    @Test
    public void deleteValid() {
        Computer newComputer = new Computer.ComputerBuilder().name("computer198494")
                .introduced(LocalDate.of(1990, 10, 10)).discontinued(LocalDate.of(2000, 10, 10))
                .company(new Company.CompanyBuilder().id(2).name("Thinking Machines").build()).build();
        newComputer.setId(computerDao.create(newComputer));
        computerDao.delete(newComputer.getId());
        Computer computerFound = computerDao.findByName("computer198494");
        // System.out.println(computerFound);
        // System.out.println(nullComputer);
        Assert.assertTrue(computerFound.equals(nullComputer));
    }

    /**
     * Test to delete all Computers with a specific Company id.
     */
    @Test
    public void deleteWithCompanyIdIsValid() {
        Company company1 = new Company.CompanyBuilder().name("company1").build();
        company1.setId(companyDao.create(company1));

        Computer computer1 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer1").company(companyDao.find(company1.getId()))
                .build();
        Computer computer2 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer2").company(companyDao.find(company1.getId()))
                .build();
        Computer computer3 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer3").company(companyDao.find(company1.getId()))
                .build();
        computer1.setId(computerDao.create(computer1));
        computer2.setId(computerDao.create(computer2));
        computer3.setId(computerDao.create(computer3));

        computerDao.deleteWithCompanyId(company1.getId());

        computer1 = computerDao.findByName("computer1");
        computer2 = computerDao.findByName("computer2");
        computer3 = computerDao.findByName("computer3");

        companyDao.delete(company1);

        Assert.assertTrue(computer1.equals(nullComputer));
        Assert.assertTrue(computer2.equals(nullComputer));
        Assert.assertTrue(computer3.equals(nullComputer));
    }

}
