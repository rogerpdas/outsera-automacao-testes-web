package com.saucedemo.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * JUnit Runner for all features.
 * Used for unified ExtentReport generation.
 */
@RunWith(Cucumber.class)
@CucumberOptions(
        features  = "src/test/resources/features",
        glue      = {"com.saucedemo.steps", "com.saucedemo.hooks"},
        plugin    = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        tags      = "not @wip",
        monochrome = true
)
public class RunAllTests {}
