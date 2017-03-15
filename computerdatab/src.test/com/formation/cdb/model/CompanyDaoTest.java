package com.formation.cdb.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Company;
import com.formation.cdb.persistence.CompanyDao;
import com.formation.cdb.persistence.PersistenceManager;

public class CompanyDaoTest {

	Company company = null;

	@BeforeClass
	public static void executerBeforeClass() throws Exception {
		PersistenceManager.INSTANCE.connectToDb();
	}

	@AfterClass
	public static void executerAfterClass() throws Exception {
		PersistenceManager.INSTANCE.close();
	}

	@Before
	public void executerAvantChaqueTest() {
		company = new Company();
		company.setName("boitetropbien");
	}

	@After
	public void executerApresChaqueTest() {
		company = null;
	}

	@Test
	public void findIsValid() {
		Company companyFound = CompanyDao.INSTANCE.find(1);
		Assert.assertTrue(companyFound.toString().equals(
				"Company [id=1, name=Apple Inc.]"));
	}

	@Test
	public void createValid() {
		company.setId(CompanyDao.INSTANCE.create(company));
		Company companyFound = CompanyDao.INSTANCE.find(company.getId());
		System.out.println(companyFound);
		System.out.println(company);		
		Assert.assertTrue(companyFound.toString().equals(company.toString()));
		CompanyDao.INSTANCE.delete(company);
	}
	
	@Test
	public void updateValid() {
		company.setId(CompanyDao.INSTANCE.create(company));
		company.setName("boitetropbien2");
		CompanyDao.INSTANCE.update(company);
		Company companyFound = CompanyDao.INSTANCE.find(company.getId());
		System.out.println(companyFound.toString());
		System.out.println(company.toString());
		Assert.assertTrue(companyFound.toString().equals(company.toString()));
		CompanyDao.INSTANCE.delete(company);
	}
	
	
	
	@Test
	public void deleteValid() {
		Company nullCompany = new Company();
		Company companyFound = CompanyDao.INSTANCE.findByName("boitetropbien");
		Assert.assertTrue(companyFound.toString().equals(nullCompany.toString()));	
	}

}
