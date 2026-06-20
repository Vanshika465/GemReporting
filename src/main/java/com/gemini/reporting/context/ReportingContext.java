package com.gemini.reporting.context;

import org.openqa.selenium.WebDriver;

public class ReportingContext {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> apiLog = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> pwScreenshot = new ThreadLocal<>();

    // ---------------- SELENIUM ----------------
    public static void setDriver(WebDriver webDriver) {
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    // ---------------- API ----------------
    public static void setApiLog(String log) {
        apiLog.set(log);
    }

    public static String getApiLog() {
        return apiLog.get();
    }

    // ---------------- PLAYWRIGHT ----------------
    public static void setPwScreenshot(byte[] screenshot) {
        pwScreenshot.set(screenshot);
    }

    public static byte[] getPwScreenshot() {
        return pwScreenshot.get();
    }

    // ---------------- CLEANUP ----------------
    public static void unload() {
        driver.remove();
        apiLog.remove();
        pwScreenshot.remove();
    }
}
