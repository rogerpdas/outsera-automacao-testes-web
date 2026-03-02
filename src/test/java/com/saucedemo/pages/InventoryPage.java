package com.saucedemo.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * Page Object for the Inventory (Products) page.
 */
@Slf4j
public class InventoryPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuButton;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    private static final String ADD_TO_CART_BUTTON_TEMPLATE = "[data-test='add-to-cart-%s']";
    private static final String REMOVE_BUTTON_TEMPLATE      = "[data-test='remove-%s']";

    public InventoryPage() {
        super();
    }

    public boolean isLoaded() {
        try {
            waitForVisibility(pageTitle);
            return "Products".equalsIgnoreCase(getText(pageTitle));
        } catch (Exception e) {
            return false;
        }
    }

    public String getPageTitle() {
        return getText(pageTitle);
    }

    public InventoryPage addItemToCartByName(String itemSlug) {
        log.info("Adding item to cart: {}", itemSlug);
        By addButton = By.cssSelector(String.format(ADD_TO_CART_BUTTON_TEMPLATE, itemSlug));
        click(waitForClickable(addButton));
        return this;
    }

    public InventoryPage removeItemFromCartByName(String itemSlug) {
        log.info("Removing item from cart: {}", itemSlug);
        By removeButton = By.cssSelector(String.format(REMOVE_BUTTON_TEMPLATE, itemSlug));
        click(waitForClickable(removeButton));
        return this;
    }

    public CartPage goToCart() {
        log.info("Navigating to cart.");
        click(cartLink);
        return new CartPage();
    }

    public int getCartItemCount() {
        try {
            return Integer.parseInt(getText(cartBadge));
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCartBadgeDisplayed() {
        return isDisplayed(cartBadge);
    }

    public int getInventoryItemCount() {
        return inventoryItems.size();
    }

    public void logout() {
        log.info("Logging out.");
        click(burgerMenuButton);
        waitForVisibility(logoutLink);
        click(logoutLink);
    }
}
