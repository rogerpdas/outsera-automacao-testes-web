package com.saucedemo.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.saucedemo.config.ConfigManager;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

/**
 * Thread-safe ExtentReports manager for parallel test execution.
 */
@Slf4j
public class ExtentReportManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();

    private static final String REPORT_DIR = "reports/";
    private static final String REPORT_FILE = REPORT_DIR + "ExtentReport.html";

    private ExtentReportManager() {}

    public static synchronized void initReport() {
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(REPORT_FILE);
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("SauceDemo E2E Test Report");
            sparkReporter.config().setReportName("Automation Test Results");
            sparkReporter.config().setTimeStampFormat("dd/MM/yyyy HH:mm:ss");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            extent.setSystemInfo("Application", "SauceDemo");
            extent.setSystemInfo("Environment", ConfigManager.getBaseUrl());
            extent.setSystemInfo("Browser", ConfigManager.getBrowser());
            extent.setSystemInfo("Execution Date",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            extent.setSystemInfo("Tester", System.getProperty("user.name", "CI Pipeline"));

            log.info("ExtentReport initialized: {}", REPORT_FILE);
        }
    }

    public static synchronized void createTest(String testName, String tags) {
        ExtentTest test = extent.createTest(testName)
                .assignCategory(tags.isEmpty() ? "Regression" : tags);
        testThreadLocal.set(test);
    }

    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }

    public static void logPass(String message) {
        ExtentTest test = getTest();
        if (test != null) test.pass(message);
    }

    public static void logFail(String message) {
        ExtentTest test = getTest();
        if (test != null) test.fail(message);
    }

    public static void logInfo(String message) {
        ExtentTest test = getTest();
        if (test != null) test.info(message);
    }

    public static void logWarning(String message) {
        ExtentTest test = getTest();
        if (test != null) test.warning(message);
    }

    public static void attachScreenshot(byte[] screenshotBytes) {
        ExtentTest test = getTest();
        if (test != null && screenshotBytes != null) {
            try {
                String base64Screenshot = Base64.getEncoder().encodeToString(screenshotBytes);
                test.fail("Step failed - Screenshot attached:",
                        MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
            } catch (Exception e) {
                log.warn("Could not attach screenshot to report: {}", e.getMessage());
            }
        }
    }

    public static synchronized void flushReport() {
        if (extent != null) {
            extent.flush();
            testThreadLocal.remove();
            log.info("ExtentReport flushed and saved to: {}", REPORT_FILE);
        }
    }
}
