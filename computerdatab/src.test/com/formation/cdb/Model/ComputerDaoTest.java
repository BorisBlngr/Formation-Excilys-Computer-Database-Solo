package com.formation.cdb.Model;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.Computer;
import com.formation.cdb.persistence.PersistenceManager;

public class ComputerDaoTest {

	Computer computer = null;

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
		computer = new Computer();
		Date introducedD = new Date(12000);
		Date discontinuedD = new Date(18000);

		computer.setCompanyId(2);
		computer.setId(3);
		computer.setName("test nom ordi random");
		computer.setIntroduced(introducedD.toLocalDate());
		computer.setDiscontinued(discontinuedD.toLocalDate());
	}

	@After
	public void executerApresChaqueTest() {
		computer = null;
	}

	@Test
	public void testCrud() {
		
	}

}
