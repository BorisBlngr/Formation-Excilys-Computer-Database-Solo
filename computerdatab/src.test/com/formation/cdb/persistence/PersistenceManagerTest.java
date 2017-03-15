package com.formation.cdb.persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersistenceManagerTest {
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

	}

	@After
	public void executerApresChaqueTest() {
	}

	@Test
	public void isAdmissible() {
	}

}