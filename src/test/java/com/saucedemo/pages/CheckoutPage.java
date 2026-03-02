package com.saucedemo.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Checkout flow (Step One & Step Two / Overview).
 */
@Slf4j
public class CheckoutPage extends BasePage {

    // Step One
    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement postalCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // Step Two (Overview)
    @FindBy(className = "summary_info")
    private WebElement summaryInfo;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    @FindBy(id = "cancel")
    private WebElement cancelButton;

    public CheckoutPage() {
        super();
    }

    public boolean isStepOneLoaded() {
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement(pageTitle, "Checkout: Your Information"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isStepTwoLoaded() {
        try {
            wait.until(org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElement(pageTitle, "Checkout: Overview"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public CheckoutPage enterFirstName(String firstName) {
        log.info("Entering first name.");
        type(firstNameField, firstName);
        return this;
    }

    public CheckoutPage enterLastName(String lastName) {
        log.info("Entering last name.");
        type(lastNameField, lastName);
        return this;
    }

    public CheckoutPage enterPostalCode(String postalCode) {
        log.info("Entering postal code.");
        type(postalCodeField, postalCode);
        return this;
    }

    public CheckoutPage fillShippingInfo(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
        return this;
    }

    public CheckoutPage clickContinue() {
        log.info("Clicking continue button.");
        click(continueButton);
        return this;
    }

    public CheckoutPage clickContinueWithEmptyForm() {
        log.info("Clicking continue with empty form.");
        click(continueButton);
        return this;
    }

    public OrderConfirmationPage finishOrder() {
        log.info("Clicking finish button.");
        click(finishButton);
        return new OrderConfirmationPage();
    }

    public CartPage cancelCheckout() {
        log.info("Cancelling checkout.");
        click(cancelButton);
        return new CartPage();
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage, 5);
    }

    public String getOrderTotal() {
        return getText(totalLabel);
    }

    public boolean isSummaryDisplayed() {
        return isDisplayed(summaryInfo);
    }
}
