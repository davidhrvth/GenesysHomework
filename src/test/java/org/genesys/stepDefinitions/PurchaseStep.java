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
import org.openqa.selenium.support.ui.Wait;

import java.util.ArrayList;

public class PurchaseStep {
    private final WebDriver driver = TestRunner.driver;
    private static final Logger logger = LogManager.getLogger(PurchaseStep.class);
    @Given("user is in products page")
    public void user_is_in_products_page() {
        driver.navigate().to("https://www.saucedemo.com/inventory.html");
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
        System.out.println("Cart size is valid: " + itemsCount);
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
}

