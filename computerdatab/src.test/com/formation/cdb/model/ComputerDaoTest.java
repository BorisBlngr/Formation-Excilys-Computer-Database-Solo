package com.formation.cdb.model;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.ComputerDao;
import com.formation.cdb.persistence.PersistenceManager;

public class ComputerDaoTest {

	Computer computer = null;
	ComputerDao computerDao = null;

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
		computer = new Computer();
		LocalDate introduced = LocalDate.of(1980, 10, 10);
		LocalDate discontinued = LocalDate.of(1990, 10, 10);
		Date introducedD = Date.valueOf(introduced);
		Date discontinuedD = Date.valueOf(discontinued);
		computer.setCompanyId(2);
		computer.setName("test nom ordi random");
		computer.setIntroduced(introducedD.toLocalDate());
		computer.setDiscontinued(discontinuedD.toLocalDate());
	}

	@After
	public void executerApresChaqueTest() {
		computer = null;
		computerDao = null;
	}

	@Test
	public void findIsValid() {
		Computer computerFound = computerDao.find(1);
		Assert.assertTrue(computerFound.toString().equals(
				"Computer [id=1, name=MacBook Pro 15.4 inch, introduced=null, companyId=1, discontinued=null]"));
	}

	@Test
	public void createValid() {
		Computer newComputer = new Computer();
		newComputer.setName("Test unitaire create name");
		newComputer.setId(computerDao.create(newComputer));
		Computer computerFound = computerDao.find(newComputer.getId());
		Assert.assertTrue(computerFound.toString().equals(newComputer.toString()));
		computerDao.delete(newComputer);
	}
	
	@Test
	public void updateValid() {
		Computer newComputer = new Computer();
		newComputer.setName("Test unitaire create name");
		newComputer.setId(computerDao.create(newComputer));
		newComputer.setName("Test unitaire create name2");
		computerDao.update(newComputer);
		Computer computerFound = computerDao.find(newComputer.getId());
		System.out.println(computerFound.toString());
		System.out.println(newComputer.toString());
		Assert.assertTrue(computerFound.toString().equals(newComputer.toString()));
		computerDao.delete(newComputer);
	}
	
	
	
	@Test
	public void deleteValid() {
		Computer nullComputer = new Computer();
		Computer computerFound = computerDao.findByName("Test unitaire create name");
		Assert.assertTrue(computerFound.toString().equals(nullComputer.toString()));	
	}
	
	

}
