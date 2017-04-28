package com.formation.cdb.model;

import java.time.LocalDate;

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
import com.formation.cdb.persistence.repository.ComputerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-module.xml"})
public class ComputerRepositoryTest {

    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    ComputerRepository computerRepository;

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
        Computer computerFound = computerRepository.findOne((long) 1);
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
        newComputer = computerRepository.save(newComputer);
        Computer computerFound = computerRepository.findOne(newComputer.getId());
        // System.out.println(newComputer);
        // System.out.println(computerFound);
        Assert.assertTrue(computerFound.toString().equals(newComputer.toString()));
        computerRepository.delete(newComputer);
    }

    /**
     * Test Update.
     */
    @Test
    public void updateValid() {
        Computer newComputer = new Computer.ComputerBuilder().name("Test unitaire create name")
                .introduced(LocalDate.of(1990, 10, 10)).discontinued(LocalDate.of(2000, 10, 10))
                .company(new Company.CompanyBuilder().id(2).name("Thinking Machines").build()).build();
        newComputer = computerRepository.save(newComputer);
        newComputer.setName("Test unitaire create name2");
        computerRepository.save(newComputer);
        Computer computerFound = computerRepository.save(newComputer);
        // System.out.println(computerFound.toString());
        // System.out.println(newComputer.toString());
        Assert.assertTrue(computerFound.equals(newComputer));
        computerRepository.delete(newComputer);
    }

    /**
     * Test delete.
     */
    @Test
    public void deleteValid() {
        Computer newComputer = new Computer.ComputerBuilder().name("computer198494")
                .introduced(LocalDate.of(1990, 10, 10)).discontinued(LocalDate.of(2000, 10, 10))
                .company(new Company.CompanyBuilder().id(2).name("Thinking Machines").build()).build();
        newComputer = computerRepository.save(newComputer);
        computerRepository.delete(newComputer.getId());
        Computer computerFound = computerRepository.findOne(newComputer.getId());
        // System.out.println(computerFound);
        // System.out.println(nullComputer);
        Assert.assertTrue(computerFound == null);
    }

    /**
     * Test to delete all Computers with a specific Company id.
     */
    @Test
    public void deleteWithCompanyIdIsValid() {
        Company company1 = new Company();
        company1.setName("company1");
        company1 = companyRepository.save(company1);

        Computer computer1 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer1")
                .company(companyRepository.findOne(company1.getId())).build();
        Computer computer2 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer2")
                .company(companyRepository.findOne(company1.getId())).build();
        Computer computer3 = new Computer.ComputerBuilder().introduced(LocalDate.of(1980, 10, 10))
                .discontinued(LocalDate.of(1990, 10, 10)).name("computer3")
                .company(companyRepository.findOne(company1.getId())).build();
        computer1 = computerRepository.save(computer1);
        computer2 = computerRepository.save(computer2);
        computer3 = computerRepository.save(computer3);

        computerRepository.deleteByCompanyId(company1.getId());

        computer1 = computerRepository.findOne(computer1.getId());
        computer2 = computerRepository.findOne(computer2.getId());
        computer3 = computerRepository.findOne(computer3.getId());

        companyRepository.delete(company1);

        Assert.assertTrue(computer1 == null);
        Assert.assertTrue(computer2 == null);
        Assert.assertTrue(computer3 == null);
    }
}
