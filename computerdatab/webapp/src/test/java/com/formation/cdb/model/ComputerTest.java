package com.formation.cdb.model;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ComputerTest {

    Computer computer = null;

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
        computer = new Computer.ComputerBuilder().name("toto").id(36).introduced(LocalDate.of(1990, 10, 10))
                .discontinued(LocalDate.of(1999, 10, 10))
                .company(new Company.CompanyBuilder().name("titi").id(6).build()).build();
    }

    /**
     * Execute after test.
     */
    @After
    public void executerApresChaqueTest() {
        computer = null;
    }

    /**
     * Test builder.
     */
    @Test
    public void builderIsValid() {
        // System.out.println(computer);
        assertTrue(computer.toString().equals(
                "Computer [id=36, name=toto, introduced=1990-10-10, discontinued=1999-10-10, company=Company [id=6, name=titi]]"));
    }

}
