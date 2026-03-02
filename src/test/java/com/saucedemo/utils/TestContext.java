package com.saucedemo.utils;

import com.saucedemo.pages.*;
import com.saucedemo.validators.CheckoutValidator;
import com.saucedemo.validators.LoginValidator;

/**
 * Shared test context for PicoContainer dependency injection.
 * Holds shared page and validator instances across step classes in the same scenario.
 */
public class TestContext {

    // Pages
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;
    private OrderConfirmationPage orderConfirmationPage;

    // Validators
    private LoginValidator loginValidator;
    private CheckoutValidator checkoutValidator;

    // Shared Step Data
    private java.util.Map<String, Object> testContextData = new java.util.HashMap<>();

    public java.util.Map<String, Object> getTestContextData() {
        return testContextData;
    }

    // Lazy initializers - Pages
    public LoginPage getLoginPage() {
        if (loginPage == null) loginPage = new LoginPage();
        return loginPage;
    }

    public InventoryPage getInventoryPage() {
        if (inventoryPage == null) inventoryPage = new InventoryPage();
        return inventoryPage;
    }

    public CartPage getCartPage() {
        if (cartPage == null) cartPage = new CartPage();
        return cartPage;
    }

    public void setCartPage(CartPage cartPage) {
        this.cartPage = cartPage;
    }

    public CheckoutPage getCheckoutPage() {
        if (checkoutPage == null) checkoutPage = new CheckoutPage();
        return checkoutPage;
    }

    public void setCheckoutPage(CheckoutPage checkoutPage) {
        this.checkoutPage = checkoutPage;
    }

    public OrderConfirmationPage getOrderConfirmationPage() {
        if (orderConfirmationPage == null) orderConfirmationPage = new OrderConfirmationPage();
        return orderConfirmationPage;
    }

    public void setOrderConfirmationPage(OrderConfirmationPage orderConfirmationPage) {
        this.orderConfirmationPage = orderConfirmationPage;
    }

    // Lazy initializers - Validators
    public LoginValidator getLoginValidator() {
        if (loginValidator == null) {
            loginValidator = new LoginValidator(getLoginPage(), getInventoryPage());
        }
        return loginValidator;
    }

    public CheckoutValidator getCheckoutValidator() {
        if (checkoutValidator == null) {
            checkoutValidator = new CheckoutValidator(
                    getInventoryPage(), getCartPage(), getCheckoutPage(), getOrderConfirmationPage()
            );
        }
        return checkoutValidator;
    }
}
