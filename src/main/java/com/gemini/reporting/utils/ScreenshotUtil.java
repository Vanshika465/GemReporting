package com.gemini.reporting.utils;

import org.openqa.selenium.*;

public class ScreenshotUtil {

    public static byte[] getScreenshotBytes(WebDriver driver) {

        if (driver == null) {
            return new byte[0];
        }

        return ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.BYTES);
    }
}