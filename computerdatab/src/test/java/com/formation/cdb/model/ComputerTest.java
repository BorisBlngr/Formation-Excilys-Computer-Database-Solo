package com.formation.cdb.model;

import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.formation.cdb.model.dto.ComputerDto;

public class ComputerTest {

    ComputerDto computer = null;

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
        computer = new ComputerDto();
        Date introducedD = new Date(12000);
        Date discontinuedD = new Date(18000);

        computer.setCompanyId(2);
        computer.setId(3);
        computer.setName("Orditropbien");
        computer.setIntroduced(introducedD.toLocalDate());
        computer.setDiscontinued(discontinuedD.toLocalDate());
    }

    /**
     * Execute after test.
     */
    @After
    public void executerApresChaqueTest() {
        computer = null;
    }

    /**
     * Test Random.
     */
    @Test
    public void testRandom() {
        int a = 1;
        int b = 1;
        assertTrue(a == b);
    }

}
