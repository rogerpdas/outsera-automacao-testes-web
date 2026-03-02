package com.saucedemo.pages;

import com.saucedemo.config.ConfigManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object for the Login page (https://www.saucedemo.com).
 */
@Slf4j
public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage() {
        super();
    }

    public LoginPage navigate() {
        navigateTo(ConfigManager.getBaseUrl());
        log.info("Navigated to Login page.");
        return this;
    }

    public LoginPage enterUsername(String username) {
        log.info("Entering username: {}", username);
        type(usernameField, username);
        return this;
    }

    public LoginPage enterPassword(String password) {
        log.info("Entering password.");
        type(passwordField, password);
        return this;
    }

    public LoginPage clickLogin() {
        log.info("Clicking login button.");
        click(loginButton);
        return this;
    }

    public LoginPage submitEmptyForm() {
        log.info("Submitting empty login form.");
        click(loginButton);
        return this;
    }

    public LoginPage submitEmptyUsernameForm(String password) {
        log.info("Submitting form with empty username.");
        type(passwordField, password);
        click(loginButton);
        return this;
    }

    public LoginPage submitEmptyPasswordForm(String username) {
        log.info("Submitting form with empty password.");
        type(usernameField, username);
        click(loginButton);
        return this;
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessage);
    }

    public boolean isUsernameFieldDisplayed() {
        return isDisplayed(usernameField);
    }

    public boolean isLoginPageLoaded() {
        return isDisplayed(loginButton) && isDisplayed(usernameField);
    }
}
