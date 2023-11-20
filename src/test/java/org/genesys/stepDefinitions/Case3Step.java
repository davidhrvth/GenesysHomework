package org.genesys.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.genesys.TestRunner;
import org.genesys.helpers.WaitHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Case3Step {
    private final WebDriver driver = TestRunner.driver;
    private static final Logger logger = LogManager.getLogger(Case3Step.class);

    @Given("user opens url {string}")
    public void user_opens_url(String url) {
        driver.get(url);
        WaitHelper.waitForNSeconds(5);
    }

    @And("the user types the text with decoration into the editor")
    public void the_user_types_text_into_the_editor() {
        WebElement boldBtn = driver.findElement(By.xpath("//a[@id='cke_18']"));
        WebElement underlineBtn = driver.findElement(By.xpath("//a[@id='cke_20']"));
        WaitHelper.waitForElementToBeClickable(boldBtn);
        boldBtn.click();
        driver.switchTo().frame(0);
        WebElement body = driver.findElement(By.xpath("//body"));
        WaitHelper.waitForElementToBeClickable(body);

        body.sendKeys("Automation ");
        driver.switchTo().defaultContent();
        boldBtn.click();
        underlineBtn.click();
        driver.switchTo().frame(0);
        body.sendKeys("Test ");
        driver.switchTo().defaultContent();
        underlineBtn.click();
        driver.switchTo().frame(0);
        body.sendKeys("Example");
        driver.switchTo().defaultContent();
    }

    @Then("{string} should appear in the rich text editor")
    public void text_should_appear_in_the_rich_text_editor(String text) {
        driver.switchTo().frame(0);
        WebElement body = driver.findElement(By.xpath("//body"));
        WaitHelper.waitForElementToBeVisible(body);
        Assert.assertEquals(text, body.getText());
        logger.info("Case 3 - Rich Text Editor IS SUCCESSFUL!");
    }
}
