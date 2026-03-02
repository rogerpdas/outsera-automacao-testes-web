package com.saucedemo.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Order Confirmation page (Checkout Complete).
 */
@Slf4j
public class OrderConfirmationPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "complete-header")
    private WebElement confirmationHeader;

    @FindBy(className = "complete-text")
    private WebElement confirmationText;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    public OrderConfirmationPage() {
        super();
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(confirmationHeader);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public String getConfirmationHeader() {
        return getText(confirmationHeader);
    }

    public String getConfirmationText() {
        return getText(confirmationText);
    }

    public boolean isOrderSuccessful() {
        return isLoaded() && getConfirmationHeader().contains("Thank you for your order");
    }

    public InventoryPage backToProducts() {
        log.info("Going back to products.");
        click(backToProductsButton);
        return new InventoryPage();
    }
}
