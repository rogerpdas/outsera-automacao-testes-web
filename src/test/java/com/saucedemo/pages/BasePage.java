package com.saucedemo.pages;

import com.saucedemo.config.ConfigManager;
import com.saucedemo.config.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Base Page Object containing reusable Selenium utility methods.
 * All page classes must extend this class.
 */
@Slf4j
public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigManager.getExplicitWait()));
        PageFactory.initElements(driver, this);
    }

    protected WebElement waitForVisibility(By locator) {
        return waitForVisibility(locator, ConfigManager.getExplicitWait());
    }

    protected WebElement waitForVisibility(By locator, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForVisibility(WebElement element) {
        return waitForVisibility(element, ConfigManager.getExplicitWait());
    }

    protected WebElement waitForVisibility(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.visibilityOf(element));
    }

    protected WebElement waitForClickable(By locator) {
        return waitForClickable(locator, ConfigManager.getExplicitWait());
    }

    protected WebElement waitForClickable(By locator, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitForClickable(WebElement element) {
        return waitForClickable(element, ConfigManager.getExplicitWait());
    }

    protected WebElement waitForClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return customWait.until(ExpectedConditions.elementToBeClickable(element));
    }

    protected void click(WebElement element) {
        try {
            waitForClickable(element).click();
        } catch (ElementClickInterceptedException e) {
            log.warn("Click intercepted, trying JavaScript click.");
            jsClick(element);
        }
    }

    protected void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected void type(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText().trim();
    }

    protected boolean isDisplayed(WebElement element) {
        return isDisplayed(element, 0); // Default to fast check
    }

    protected boolean isDisplayed(WebElement element, int timeoutInSeconds) {
        try {
            if (timeoutInSeconds > 0) {
                waitForVisibility(element, timeoutInSeconds);
            }
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException | StaleElementReferenceException e) {
            return false;
        }
    }

    protected boolean isDisplayed(By locator) {
        return isDisplayed(locator, 0); // Default to fast check
    }

    protected boolean isDisplayed(By locator, int timeoutInSeconds) {
        try {
            if (timeoutInSeconds > 0) {
                waitForVisibility(locator, timeoutInSeconds);
            }
            return driver.findElement(locator).isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }

    protected boolean waitForInvisibility(By locator, int timeoutInSeconds) {
        try {
            WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return customWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
    }

    protected void navigateTo(String url) {
        log.info("Navigating to: {}", url);
        driver.get(url);
    }

    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    protected String getPageTitle() {
        return driver.getTitle();
    }

    protected void waitForUrlContains(String partialUrl) {
        wait.until(ExpectedConditions.urlContains(partialUrl));
    }

    protected void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    protected void takeScreenshotOnFailure(String stepName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            log.debug("Screenshot captured for step: {}", stepName);
        } catch (Exception e) {
            log.warn("Could not capture screenshot: {}", e.getMessage());
        }
    }
}
