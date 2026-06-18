package com.company.reporting.serenity;

import net.serenitybdd.core.Serenity;

public class SerenityHelper {

    public static void captureFailureScreenshot() {
        Serenity.takeScreenshot();
    }
}