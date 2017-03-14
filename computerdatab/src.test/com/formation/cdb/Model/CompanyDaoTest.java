package com.formation.cdb.Model;

import static org.junit.Assert.*;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Company;
import com.formation.cdb.model.CompanyDao;
import com.formation.cdb.model.Computer;
import com.formation.cdb.model.ComputerDao;
import com.formation.cdb.persistence.PersistenceManager;

public class CompanyDaoTest {

	Company company = null;
	CompanyDao companyDao = null;

	@BeforeClass
	public static void executerBeforeClass() throws Exception {
		PersistenceManager.getInstance().connectToDb();
	}

	@AfterClass
	public static void executerAfterClass() throws Exception {
		PersistenceManager.getInstance().close();
	}

	@Before
	public void executerAvantChaqueTest() {
		company = new Company();
		companyDao = new CompanyDao(PersistenceManager.getInstance().getConn());
		company.setName("boitetropbien");
	}

	@After
	public void executerApresChaqueTest() {
		company = null;
		companyDao = null;
	}

	@Test
	public void findIsValid() {
		Company companyFound = companyDao.find(1);
		Assert.assertTrue(companyFound.toString().equals(
				"Company [id=1, name=Apple Inc.]"));
	}

	@Test
	public void createValid() {
		company.setId(companyDao.create(company));
		Company companyFound = companyDao.find(company.getId());
		Assert.assertTrue(companyFound.toString().equals(company.toString()));
		companyDao.delete(company);
	}
	
	@Test
	public void updateValid() {
		company.setId(companyDao.create(company));
		company.setName("boitetropbien2");
		companyDao.update(company);
		Company companyFound = companyDao.find(company.getId());
		System.out.println(companyFound.toString());
		System.out.println(company.toString());
		Assert.assertTrue(companyFound.toString().equals(company.toString()));
		companyDao.delete(company);
	}
	
	
	
	@Test
	public void deleteValid() {
		Company nullCompany = new Company();
		Company companyFound = companyDao.findByName("boitetropbien");
		Assert.assertTrue(companyFound.toString().equals(nullCompany.toString()));	
	}

}
