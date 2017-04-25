package com.formation.cdb.ui;

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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.is;

public class AddEditDeleteTest {
    private WebDriver driver;
    private String baseUrl;
    private StringBuffer verificationErrors = new StringBuffer();
    Parameters params = new Parameters();
    Configuration config;
    final Logger logger = LoggerFactory.getLogger(SearchPaginationTest.class);
    private final int TIMEOUT = 30;

    /**
     * @throws Exception Exception
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
     * @throws Exception Exception
     */
    @Test
    public void addEditDelete() throws Exception {
        driver.get(baseUrl + "/computerdatab/dashboard");
        driver.findElement(By.id("addComputer")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("00000000000000000000000000000");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.linkText("Application - Computer Database")).click();
        assertEquals("00000000000000000000000000000",
                driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
        driver.findElement(By.linkText("00000000000000000000000000000")).click();
        driver.findElement(By.id("computerName")).clear();
        driver.findElement(By.id("computerName")).sendKeys("111111111111111111111111111111");
        driver.findElement(By.cssSelector("input.btn.btn-primary")).click();
        driver.findElement(By.linkText("Application - Computer Database")).click();
        assertEquals("111111111111111111111111111111",
                driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText());
        driver.findElement(By.id("editcomputer")).click();
        driver.findElement(By.name("cb")).click();
        driver.findElement(By.id("deleteSelected")).click();
        driver.switchTo().alert().accept();
        assertThat("111111111111111111111111111111",
                is(not(driver.findElement(By.xpath("//tbody[@id='results']/tr/td[2]")).getText())));
    }

    /**
     * @throws Exception Exception
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
