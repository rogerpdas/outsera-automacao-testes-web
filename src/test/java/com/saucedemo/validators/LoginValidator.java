package com.saucedemo.validators;

import com.saucedemo.pages.InventoryPage;
import com.saucedemo.pages.LoginPage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;

/**
 * Validator class responsible for all login-related assertions.
 * Steps must delegate assertions to this class.
 */
@Slf4j
public class LoginValidator {

    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;

    public LoginValidator(LoginPage loginPage, InventoryPage inventoryPage) {
        this.loginPage = loginPage;
        this.inventoryPage = inventoryPage;
    }

    public void assertLoginSuccessful() {
        log.info("Validating successful login.");
        Assert.assertTrue(
                "User should be redirected to inventory page after successful login.",
                inventoryPage.isLoaded()
        );
        Assert.assertEquals(
                "Inventory page title should be 'Products'.",
                "Products",
                inventoryPage.getPageTitle()
        );
        log.info("Login validation passed: user is on Products page.");
    }

    public void assertLoginFailed(String expectedMessage) {
        log.info("Validating failed login with expected message: '{}'", expectedMessage);
        Assert.assertTrue(
                "Error message should be displayed for invalid login attempt.",
                loginPage.isErrorDisplayed()
        );
        Assert.assertTrue(
                String.format("Error message should contain '%s' but got '%s'",
                        expectedMessage, loginPage.getErrorMessage()),
                loginPage.getErrorMessage().contains(expectedMessage)
        );
        log.info("Login failure validation passed.");
    }

    public void assertUsernameRequiredError() {
        log.info("Validating username required error.");
        Assert.assertTrue("Error message should be displayed.", loginPage.isErrorDisplayed());
        Assert.assertTrue(
                "Error should indicate username is required.",
                loginPage.getErrorMessage().contains("Username is required")
        );
    }

    public void assertPasswordRequiredError() {
        log.info("Validating password required error.");
        Assert.assertTrue("Error message should be displayed.", loginPage.isErrorDisplayed());
        Assert.assertTrue(
                "Error should indicate password is required.",
                loginPage.getErrorMessage().contains("Password is required")
        );
    }

    public void assertLockedUserError() {
        log.info("Validating locked user error.");
        Assert.assertTrue("Error message should be displayed for locked user.", loginPage.isErrorDisplayed());
        Assert.assertTrue(
                "Error should indicate user is locked out.",
                loginPage.getErrorMessage().contains("locked out")
        );
    }

    public void assertUserRemainsOnLoginPage() {
        log.info("Validating user remains on login page.");
        Assert.assertTrue(
                "Login page should still be visible.",
                loginPage.isLoginPageLoaded()
        );
        Assert.assertFalse(
                "Inventory page should NOT be loaded.",
                inventoryPage.isLoaded()
        );
    }
}
