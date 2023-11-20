package org.genesys;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "org/genesys/stepDefinitions" },
                 tags = "@regression",
                 features = { "src/test/resources/features/"
                 })
public class TestRunner {
    public static WebDriver driver;
    private static final Logger logger = LogManager.getLogger(TestRunner.class);

    @BeforeClass
    public static void setup() {
        logger.info("Setting up tests...");
        driver = new FirefoxDriver();
    }

    @AfterClass
    public static void teardown() {
        logger.info("Tearing down tests...");
        driver.quit();
        driver = null;
    }

}
