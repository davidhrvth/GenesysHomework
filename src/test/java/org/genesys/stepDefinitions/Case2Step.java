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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class Case2Step {

    private final WebDriver driver = TestRunner.driver;
    private static final Logger logger = LogManager.getLogger(Case2Step.class);


    @Given("user tries to access inventory page without being logged in")
    public void user_tries_to_access_inventory_page_without_being_logged_in() {
        driver.get("https://www.saucedemo.com/inventory.html");
    }

    @And("is redirected to login page")
    public void is_redirected_to_login_page() {
        WaitHelper.waitForNSeconds(3);
        Assert.assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }

    @Then("correct reasoning should be given as to why they were redirected")
    public void correct_reasoning_should_be_given_as_to_why_they_were_redirected() {
        WebElement errorBox = driver.findElement(By.xpath("//h3[@data-test='error']"));
        WaitHelper.waitForElementToBeVisible(errorBox);
        Assert.assertEquals("Epic sadface: You can only access '/inventory.html' when you are logged in.", errorBox.getText());
    }

    @When("the user scrolls to the bottom of the page")
    public void the_user_scrolls_to_the_bottom_of_the_page() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    @Then("the footer should contain {string}")
    public void the_footer_should_contain(String content) {
        WebElement footer = driver.findElement(By.xpath("//footer"));
        WaitHelper.waitForElementToBeVisible(footer);
        String[] expectedFooterContent = content.split(",");
        for (String s : expectedFooterContent) {
            Assert.assertTrue(footer.getText().contains(s.trim()));
        }
        logger.info("Case 2 – Verify error messages for mandatory fields IS SUCCESSFUL!");
    }
}
