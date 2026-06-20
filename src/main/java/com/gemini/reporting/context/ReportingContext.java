
package com.gemini.reporting.context;

import org.openqa.selenium.WebDriver;

public class ReportingContext {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> apiLog = new ThreadLocal<>();

    // ---------------- SELENIUM ----------------
    public static void setDriver(WebDriver webDriver) {
        System.out.println("SET DRIVER CALLED");
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    // ---------------- REST ASSURED ----------------
    public static void setApiLog(String log) {
        System.out.println("SET API LOG CALLED");
        apiLog.set(log);
    }

    public static String getApiLog() {
        return apiLog.get();
    }

    // ---------------- CLEANUP ----------------
    public static void unload() {
        driver.remove();
        apiLog.remove();
    }
}
