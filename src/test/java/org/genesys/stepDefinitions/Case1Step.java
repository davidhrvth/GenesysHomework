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

public class Case1Step {
    private final WebDriver driver = TestRunner.driver;
    private static final Logger logger = LogManager.getLogger(Case1Step.class);

    @Given("user is in products page")
    public void user_is_in_products_page() {
        if (!driver.getCurrentUrl().equals("https://www.saucedemo.com/inventory.html")) {
            driver.navigate().to("https://www.saucedemo.com/inventory.html");
        }
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("https://www.saucedemo.com/inventory.html", currentUrl);
    }

    @When("user adds {string} to cart")
    public void user_adds_products_to_cart(String listOfProducts) {
        String[] productNames = listOfProducts.split(",");
        for (int i = 0; i < productNames.length; i++) {
            productNames[i] = productNames[i].trim();
        }

        for (String productName : productNames) {
            WebElement addToCart = driver.findElement(By.xpath("//div[contains(text(), '" + productName +"')]/../../..//button"));
            addToCart.click();
        }
    }

    @And("verifies cart product quantity badge equals to {string} size")
    public void verifies_cart_product_quantity_badge(String listOfItems) {
        int itemsCount = listOfItems.split(",").length;
        WebElement shoppingCartBadge = driver.findElement(By.xpath("//span[@class='shopping_cart_badge']"));
        WaitHelper.waitForElementToBeVisible(shoppingCartBadge);
        Assert.assertEquals(itemsCount, Integer.parseInt(shoppingCartBadge.getText()));
        logger.info("Cart size is valid: " + itemsCount);
    }

    @And("clicks on cart icon")
    public void clicks_on_cart_icon() {
        WebElement cartIcon = driver.findElement(By.xpath("//a[@class='shopping_cart_link']"));
        WaitHelper.waitForElementToBeClickable(cartIcon);
        cartIcon.click(); //We don't need to wait for this, but we are waiting, so it's closer to the methodology used in a real project where this step could be used not just after the current previous one.
    }

    @And("clicks on checkout button")
    public void clicks_on_checkout_button() {
        WebElement checkoutButton = driver.findElement(By.xpath("//button[@id='checkout']"));
        WaitHelper.waitForElementToBeClickable(checkoutButton);
        checkoutButton.click();
    }

    @And("fills out checkout information with {string}, {string}, {string}")
    public void fillsOutCheckoutInformationWith(String firstname, String lastname, String zipcode) {
        WebElement firstnameInput = driver.findElement(By.xpath("//input[@id='first-name']"));
        WebElement lastnameInput = driver.findElement(By.xpath("//input[@id='last-name']"));
        WebElement zipCode = driver.findElement(By.xpath("//input[@id='postal-code']"));

        WaitHelper.waitForElementToBeClickable(firstnameInput); //if one input is clickable, we can assume all other inputs are in the same form

        firstnameInput.sendKeys(firstname.trim());
        lastnameInput.sendKeys(lastname.trim());
        zipCode.sendKeys(zipcode.trim());
    }

    @And("clicks continue")
    public void clicks_continue() {
        WebElement continueBtn = driver.findElement(By.xpath("//input[@id='continue']"));
        WaitHelper.waitForElementToBeClickable(continueBtn);
        continueBtn.click();
    }

    @And("clicks finish")
    public void clicks_finish() {
        WebElement finishButton = driver.findElement(By.xpath("//button[@id='finish']"));
        WaitHelper.waitForElementToBeClickable(finishButton);
        finishButton.click();
    }

    @Then("thank you for your order message should appear")
    public void thank_you_for_your_order_message_should_appear() {
        Assert.assertTrue(driver.getPageSource().contains("Thank you for your order"));
    }

    @Given("user is in thank you page")
    public void user_is_in_thank_you_page() {
        Assert.assertTrue(driver.getPageSource().contains("Thank you for your order!"));
        Assert.assertEquals("https://www.saucedemo.com/checkout-complete.html", driver.getCurrentUrl());
    }

    @Then("user logs out")
    public void user_logs_out() {
        WebElement burgerMenu = driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']"));
        WaitHelper.waitForElementToBeClickable(burgerMenu);
        burgerMenu.click();
        WebElement logoutBtn = driver.findElement(By.xpath("//a[@id='logout_sidebar_link']"));
        WaitHelper.waitForElementToBeClickable(logoutBtn);
        logoutBtn.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Case 1 – Automate Purchase Process IS SUCCESSFUL!");
    }

    @Given("the user navigates to SwagLabs login page")
    public void the_user_navigates_to_swaglabs_page() {
        driver.get("https://www.saucedemo.com/");
    }

    @When("the user fills out the login form")
    public void the_user_fills_out_the_login_form()  {
        JsonNode credentials = FileHelper.getParsedJson("credential.json");
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='user-name']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        WaitHelper.waitForElementToBeClickable(usernameInput);
        assert credentials != null;
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

    @Then("the user should be logged in successfully")
    public void the_user_should_be_logged_in_or_reasoning_given() {
        WaitHelper.waitForElementToBeVisible(driver.findElement(By.xpath("//div[@id='shopping_cart_container']")));
        logger.info("User has logged in successfully!");
    }

    @And("the user tries to log in with {string} and {string}")
    public void user_tries_to_log_in_with_username_and_password(String username, String password) {
        WebElement usernameInput = driver.findElement(By.xpath("//input[@id='user-name']"));
        WebElement passwordInput = driver.findElement(By.xpath("//input[@id='password']"));
        WaitHelper.waitForElementToBeClickable(usernameInput);
        usernameInput.sendKeys(username.trim());
        WaitHelper.waitForElementToBeClickable(passwordInput);
        passwordInput.sendKeys(password.trim());
    }

    @Given("the user navigates to SwagLabs inventory page without being authenticated")
    public void the_user_navigates_to_swaglabs_inventory_page_without_being_authenticated() throws InterruptedException {
        driver.get("https://www.saucedemo.com/inventory.html");
        Thread.sleep(5000);
    }
}

