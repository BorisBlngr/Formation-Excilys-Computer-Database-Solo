package com.formation.cdb.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchPaginationTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    Parameters params = new Parameters();
    Configuration config;
    final Logger logger = LoggerFactory.getLogger(SearchPaginationTest.class);
    private final int TIMEOUT = 30;

    /**
     * @throws Exception Exception.
     */
    @Before
    public void setUp() throws Exception {
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(
                PropertiesConfiguration.class).configure(params.properties().setFileName("conf.properties"));
        try {
            config = builder.getConfiguration();

        } catch (ConfigurationException cex) {
            // loading of the configuration file failed
        }
        System.setProperty("webdriver.chrome.driver", config.getString("chromedriver"));
        driver = new ChromeDriver();
        baseUrl = "http://localhost:8080/";
        driver.manage().timeouts().implicitlyWait(TIMEOUT, TimeUnit.SECONDS);
    }

    /**
     * @throws Exception Exception.
     */
    @Test
    public void testSearchEtPagination() throws Exception {
        // (new WebDriverWait(driver,
        // TIMEOUT)).until(ExpectedConditions.presenceOfElementLocated(By.id("computerName")));
        driver.get(baseUrl + "/computerdatab/dashboard");
        driver.findElement(By.id("searchbox")).clear();
        driver.findElement(By.id("searchbox")).sendKeys("l");
        driver.findElement(By.id("searchsubmit")).click();
        assertEquals("l", driver.findElement(By.id("searchbox")).getAttribute("value"));
        driver.findElement(By.linkText("2")).click();
        assertEquals("2", driver.findElement(By.id("pageIndex")).getAttribute("value"));
        driver.findElement(By.linkText("50")).click();
        driver.findElement(By.linkText("1")).click();
        assertEquals("50", driver.findElement(By.id("maxInPage")).getAttribute("value"));
        driver.findElement(By.linkText("1")).click();
        assertEquals("1", driver.findElement(By.id("pageIndex")).getAttribute("value"));
        assertEquals("50", driver.findElement(By.id("maxInPage")).getAttribute("value"));
    }

    /**
     * @throws Exception Exception.
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
