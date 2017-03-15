package com.formation.cdb.model;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.ComputerDao;

public class ComputerDaoTest {

	Computer computer = null;

	@BeforeClass
	public static void executerBeforeClass() throws Exception {
	}

	@AfterClass
	public static void executerAfterClass() throws Exception {
	}

	@Before
	public void executerAvantChaqueTest() {
		computer = new Computer();
		LocalDate introduced = LocalDate.of(1980, 10, 10);
		LocalDate discontinued = LocalDate.of(1990, 10, 10);
		computer.setCompanyId(2);
		computer.setName("test nom ordi random");
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
	}

	@After
	public void executerApresChaqueTest() {
		computer = null;
	}

	@Test
	public void findIsValid() {
		Computer computerFound = ComputerDao.INSTANCE.find(1);
		Assert.assertTrue(computerFound.toString().equals(
				"Computer [id=1, name=MacBook Pro 15.4 inch, introduced=null, companyId=1, discontinued=null]"));
	}

	@Test
	public void createValid() {
		Computer newComputer = new Computer();
		newComputer.setName("Test unitaire create name");
		newComputer.setId(ComputerDao.INSTANCE.create(newComputer));
		Computer computerFound = ComputerDao.INSTANCE.find(newComputer.getId());
		Assert.assertTrue(computerFound.toString().equals(newComputer.toString()));
		ComputerDao.INSTANCE.delete(newComputer);
	}
	
	@Test
	public void updateValid() {
		Computer newComputer = new Computer();
		newComputer.setName("Test unitaire create name");
		newComputer.setId(ComputerDao.INSTANCE.create(newComputer));
		newComputer.setName("Test unitaire create name2");
		ComputerDao.INSTANCE.update(newComputer);
		Computer computerFound = ComputerDao.INSTANCE.find(newComputer.getId());
		System.out.println(computerFound.toString());
		System.out.println(newComputer.toString());
		Assert.assertTrue(computerFound.toString().equals(newComputer.toString()));
		ComputerDao.INSTANCE.delete(newComputer);
	}
	
	
	
	@Test
	public void deleteValid() {
		Computer nullComputer = new Computer();
		Computer computerFound = ComputerDao.INSTANCE.findByName("Test unitaire create name");
		Assert.assertTrue(computerFound.toString().equals(nullComputer.toString()));	
	}
	
	

}
