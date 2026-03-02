package com.saucedemo.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for the Shopping Cart page.
 */
@Slf4j
public class CartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    public CartPage() {
        super();
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(pageTitle);
            return "Your Cart".equalsIgnoreCase(getText(pageTitle));
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public boolean isItemInCart(String itemName) {
        return cartItemNames.stream()
                .anyMatch(el -> el.getText().equalsIgnoreCase(itemName));
    }

    public List<String> getCartItemNames() {
        return cartItemNames.stream().map(WebElement::getText).toList();
    }

    public CheckoutPage proceedToCheckout() {
        log.info("Proceeding to checkout.");
        click(checkoutButton);
        return new CheckoutPage();
    }

    public InventoryPage continueShopping() {
        log.info("Continuing shopping.");
        click(continueShoppingButton);
        return new InventoryPage();
    }
}
