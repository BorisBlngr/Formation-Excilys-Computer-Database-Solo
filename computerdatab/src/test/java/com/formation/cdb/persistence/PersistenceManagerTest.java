package com.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PersistenceManagerTest {
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

    }

    /**
     * Execute after test.
     */
    @After
    public void executerApresChaqueTest() {
    }

    /**
     * Empty test.
     */
    @Test
    public void isAdmissible() {
        String t = "";

        try (Connection conn = PersistenceManager.INSTANCE.connectToDb();) {
            t = "test";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Assert.assertTrue(t.equals("test"));
    }

}