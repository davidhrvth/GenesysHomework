package org.genesys.stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.genesys.TestRunner;
import org.genesys.helpers.WaitHelper;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;

import java.util.Set;

public class Case4Step {
    private final WebDriver driver = TestRunner.driver;
    private static final Logger logger = LogManager.getLogger(Case4Step.class);
    private String mainTabHandle = "";


    @When("user clicks on image in iFrame")
    public void user_clicks_on_image_in_iframe() {
        WebElement iFrame = driver.findElement(By.id("a077aa5e"));
        scrollToElement(iFrame);
        driver.switchTo().frame("a077aa5e");
        WebElement image = driver.findElement(By.xpath("//img"));
        WaitHelper.waitForElementToBeClickable(image);
        image.click();
        driver.switchTo().defaultContent();
    }

    @And("checks if page title is {string}")
    public void check_if_page_title_is(String title) {
        Set<String> windowHandles = driver.getWindowHandles();
        for (String windowHandle : windowHandles) {
            if (!driver.getWindowHandle().equals(windowHandle)) {
                driver.switchTo().window(windowHandle);
            } else {
                mainTabHandle = windowHandle;
            }
        }

        Assert.assertEquals(title, driver.getTitle());
    }

    @And("closes cookie window")
    public void closes_cookie_window() {
        try {
            driver.switchTo().frame("gdpr-consent-notice");
            WebElement denyCookieConsentBtn = driver.findElement(By.xpath("//span[contains(text(), 'Összes elutasítása')]"));
            WaitHelper.waitForElementToBeClickable(denyCookieConsentBtn);
            denyCookieConsentBtn.click();
            WebElement confirmDenyBtn = driver.findElement(By.xpath("//span[contains(text(), 'Elutasítás')]"));
            WaitHelper.waitForElementToBeClickable(confirmDenyBtn);
            confirmDenyBtn.click();
            driver.switchTo().defaultContent();
        } catch (NoSuchElementException ex) {
           logger.info("No cookie window, proceeding...");
        }

    }

    @And("closes tab")
    public void closes_tab() {
        driver.close();
        driver.switchTo().window(mainTabHandle);
    }

    @And("clicks on Selenium link in Testing menu")
    public void clicks_on_selenium_link_in_testing_menu() {
        WebElement testingDropdown = driver.findElement(By.xpath("//li[contains(@class, 'item118')]"));
        scrollToElement(testingDropdown);
        Actions actions = new Actions(driver);
        actions.moveToElement(testingDropdown).perform();
        WebElement seleniumLink = driver.findElement(By.xpath("//li[@class='item121']/a"));
        WaitHelper.waitForElementToBeClickable(seleniumLink);
        seleniumLink.click();
        try {
            WaitHelper.waitForNSeconds(5);
            WebElement agreeBtn = driver.findElement(By.xpath("//span[contains(text(), 'AGREE')]"));
            WaitHelper.waitForElementToBeClickable(agreeBtn);
            agreeBtn.click();
        } catch (NoSuchElementException ex) {
            logger.info("No cookie consent window, proceeding...");
        }
    }

    @Then("button with value property {string} should be displayed")
    public void btnText_button_should_be_displayed(String buttonText) {
        WebElement btn = driver.findElement(By.xpath("//input[@value='" + buttonText + "']"));
        scrollToElement(btn);
        WaitHelper.waitForElementToBeVisible(btn);
        Assert.assertTrue(btn.isDisplayed());
        logger.info("Case 4 - iFrame and tab handling IS SUCCESSFUL!");
    }

    private void scrollToElement(WebElement element) {
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
