package com.saucedemo.utils;

import com.saucedemo.config.DriverManager;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for capturing screenshots during test execution.
 */
@Slf4j
public class ScreenshotUtils {

    private static final String SCREENSHOTS_DIR = "reports/screenshots/";

    private ScreenshotUtils() {}

    public static byte[] captureScreenshot() {
        try {
            if (!DriverManager.isDriverInitialized()) {
                log.warn("Driver not initialized, cannot capture screenshot.");
                return null;
            }
            WebDriver driver = DriverManager.getDriver();
            return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            log.warn("Failed to capture screenshot: {}", e.getMessage());
            return null;
        }
    }

    public static void saveScreenshotToFile(String scenarioName) {
        byte[] screenshot = captureScreenshot();
        if (screenshot == null) return;

        try {
            Path dir = Paths.get(SCREENSHOTS_DIR);
            Files.createDirectories(dir);

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            String filename = sanitize(scenarioName) + "_" + timestamp + ".png";
            Path filePath = dir.resolve(filename);

            Files.write(filePath, screenshot);
            log.info("Screenshot saved: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            log.error("Error saving screenshot: {}", e.getMessage());
        }
    }

    private static String sanitize(String name) {
        return name.replaceAll("[^a-zA-Z0-9_\\-]", "_").replaceAll("_{2,}", "_");
    }
}
