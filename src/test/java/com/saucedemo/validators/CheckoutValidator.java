package com.saucedemo.validators;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.OrderConfirmationPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

/**
 * Validator class responsible for all checkout and cart-related assertions.
 * Steps must delegate assertions to this class.
 */
@Slf4j
public class CheckoutValidator {

    private final InventoryPage inventoryPage;
    private final CartPage cartPage;
    private final CheckoutPage checkoutPage;
    private final OrderConfirmationPage orderConfirmationPage;

    public CheckoutValidator(InventoryPage inventoryPage,
                             CartPage cartPage,
                             CheckoutPage checkoutPage,
                             OrderConfirmationPage orderConfirmationPage) {
        this.inventoryPage = inventoryPage;
        this.cartPage = cartPage;
        this.checkoutPage = checkoutPage;
        this.orderConfirmationPage = orderConfirmationPage;
    }

    public void assertItemAddedToCart(int expectedCount) {
        log.info("Validating item count in cart badge: expected={}", expectedCount);
        Assert.assertTrue("Cart badge should be visible.", inventoryPage.isCartBadgeDisplayed());
        
        int actualCount = inventoryPage.getCartItemCount();
        long endTime = System.currentTimeMillis() + 5000;
        while (actualCount != expectedCount && System.currentTimeMillis() < endTime) {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            actualCount = inventoryPage.getCartItemCount();
        }
        
        Assert.assertEquals(
                "Cart badge count should match number of items added.",
                expectedCount,
                actualCount
        );
    }

    public void assertCartPageLoaded() {
        log.info("Validating cart page is loaded.");
        Assert.assertTrue("Cart page should be loaded.", cartPage.isLoaded());
        Assert.assertEquals("Cart page title should be 'Your Cart'.", "Your Cart", cartPage.getPageTitle());
    }

    public void assertCartContainsItem(String itemName) {
        log.info("Validating cart contains item: {}", itemName);
        Assert.assertTrue(
                String.format("Cart should contain item '%s'. Current items: %s", itemName, cartPage.getCartItemNames()),
                cartPage.isItemInCart(itemName)
        );
    }

    public void assertCartItemCount(int expectedCount) {
        log.info("Validating cart item count: expected={}", expectedCount);
        Assert.assertEquals(
                "Cart should have the expected number of items.",
                expectedCount,
                cartPage.getCartItemCount()
        );
    }

    public void assertCheckoutStepOneLoaded() {
        log.info("Validating checkout step one loaded.");
        Assert.assertTrue("Checkout step one should be loaded.", checkoutPage.isStepOneLoaded());
    }

    public void assertCheckoutStepTwoLoaded() {
        log.info("Validating checkout step two (overview) loaded.");
        boolean isLoaded = checkoutPage.isStepTwoLoaded();
        long endTime = System.currentTimeMillis() + 5000;
        while (!isLoaded && System.currentTimeMillis() < endTime) {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            isLoaded = checkoutPage.isStepTwoLoaded();
        }
        Assert.assertTrue("Checkout step two (overview) should be loaded.", isLoaded);
        Assert.assertTrue("Order summary should be displayed.", checkoutPage.isSummaryDisplayed());
    }

    public void assertCheckoutInfoError(String expectedError) {
        log.info("Validating checkout error: '{}'", expectedError);
        Assert.assertTrue("Error message should be displayed.", checkoutPage.isErrorDisplayed());
        
        String actualError = checkoutPage.getErrorMessage();
        long endTime = System.currentTimeMillis() + 5000;
        while (!actualError.contains(expectedError) && System.currentTimeMillis() < endTime) {
            try { Thread.sleep(500); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            if (checkoutPage.isErrorDisplayed()) {
                actualError = checkoutPage.getErrorMessage();
            }
        }
        
        Assert.assertTrue(
                String.format("Error should contain '%s' but got '%s'",
                        expectedError, actualError),
                actualError.contains(expectedError)
        );
    }

    public void assertOrderCompleted() {
        log.info("Validating order completion.");
        Assert.assertTrue("Order confirmation page should be loaded.", orderConfirmationPage.isLoaded());
        Assert.assertTrue(
                "Confirmation header should contain 'Thank you for your order'.",
                orderConfirmationPage.isOrderSuccessful()
        );
        log.info("Order completion validation passed. Header: {}", orderConfirmationPage.getConfirmationHeader());
    }

    public void assertOrderConfirmationPageTitle() {
        log.info("Validating order confirmation page title.");
        Assert.assertEquals(
                "Page title should be 'Checkout: Complete!'",
                "Checkout: Complete!",
                orderConfirmationPage.getPageTitle()
        );
    }
}
