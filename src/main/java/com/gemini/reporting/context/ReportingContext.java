package com.gemini.reporting.context;

import org.openqa.selenium.WebDriver;

public class ReportingContext {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(WebDriver webDriver) {
        System.out.println("SET DRIVER CALLED");
        driver.set(webDriver);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void unload() {
        driver.remove();
    }
}