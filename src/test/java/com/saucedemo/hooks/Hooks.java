package com.saucedemo.hooks;

import com.saucedemo.config.DriverManager;
import com.saucedemo.utils.ScreenshotUtils;
import io.cucumber.java.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Cucumber Hooks for setup and teardown lifecycle.
 * Manages WebDriver initialization, ExtentReport steps, and screenshot on
 * failure.
 */
@Slf4j
public class Hooks {

    @BeforeAll
    public static void beforeAll() {
        log.info("========== Starting Test Suite ==========");
    }

    @Before
    public void setUp(Scenario scenario) {
        log.info("---> Starting Scenario: [{}] | Tags: {}", scenario.getName(), scenario.getSourceTagNames());
        DriverManager.initializeDriver();
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = ScreenshotUtils.captureScreenshot();
            if (screenshot != null) {
                scenario.attach(screenshot, "image/png", "Screenshot on Failure");
                log.warn("Screenshot captured after failed step in scenario: {}", scenario.getName());
            }
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        log.info("<--- Finished Scenario: [{}] | Status: {}", scenario.getName(), scenario.getStatus());

        DriverManager.quitDriver();
    }

    @AfterAll
    public static void afterAll() {
        log.info("========== Test Suite Completed | Report generated ==========");
    }
}
