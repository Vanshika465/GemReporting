package com.gemini.reporting.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public class PlaywrightUtil {

    public static void attachScreenshot(byte[] screenshot) {

        if (screenshot == null) return;

        Allure.addAttachment(
                "Playwright Failure Screenshot",
                "image/png",
                new ByteArrayInputStream(screenshot),
                "png"
        );
    }
}
