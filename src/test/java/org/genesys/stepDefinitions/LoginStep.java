package org.genesys.stepDefinitions;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.JsonNode;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.genesys.TestRunner;
import org.genesys.helpers.FileHelper;
import org.genesys.helpers.WaitHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginStep {
    private final WebDriver driver = TestRunner.driver;
    private static final Logger logger = LogManager.getLogger(LoginStep.class);


    @Given("the user navigates to SwagLabs page")
    public void the_user_navigates_to_swaglabs_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("the user fills out the login form")
    public void the_user_fills_out_the_login_form()  {
        JsonNode credentials = FileHelper.getParsedJson("credential.json");
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='user-name']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        WaitHelper.waitForElementToBeClickable(usernameInput);
        usernameInput.sendKeys(credentials.get("username").toString().replace("\"", ""));
        WaitHelper.waitForElementToBeClickable(passwordInput);
        passwordInput.sendKeys(credentials.get("password").toString().replace("\"", ""));
    }

    @And("clicks login")
    public void clicks_login() {
        WebElement loginBtn = driver.findElement(By.xpath("//input[@id='login-button']"));
        WaitHelper.waitForElementToBeClickable(loginBtn);
        loginBtn.click();
    }

    @Then("the user should be logged in successfully, or valid reasoning should be given as to why the user has not been logged in")
    public void the_user_should_be_logged_in_or_reasoning_given() {
        WaitHelper.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@id='shopping_cart_container']")));
        logger.info("User has logged in successfully!");
    }
}
