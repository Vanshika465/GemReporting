package com.gemini.reporting.context;

import org.openqa.selenium.WebDriver;

public class ReportingContext {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<String> apiLog = new ThreadLocal<>();
    private static final ThreadLocal<byte[]> pwScreenshot = new ThreadLocal<>();
    private static final ThreadLocal<StringBuilder> logs =
            ThreadLocal.withInitial(StringBuilder::new);
    private static String featureReportType;

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

    private static final ThreadLocal<String> scenarioName = new ThreadLocal<>();

    public static void setScenarioName(String name) {
        scenarioName.set(name);
    }

    public static String getScenarioName() {
        return scenarioName.get();
    }
    public static void setFeatureReportType(String reportType) {
        featureReportType = reportType;
    }

    public static String getFeatureReportType() {
        return featureReportType;
    }
    public static void log(String message) {
        logs.get().append(message).append("\n");
    }

    public static String getLogs() {
        return logs.get().toString();
    }

    // ---------------- CLEANUP ----------------
    public static void unload() {
        driver.remove();
        apiLog.remove();
        pwScreenshot.remove();
        scenarioName.remove();
        featureReportType = null;
        logs.remove();
    }
}
