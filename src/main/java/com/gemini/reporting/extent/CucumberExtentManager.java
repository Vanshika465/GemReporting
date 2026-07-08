package com.gemini.reporting.extent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.gemini.reporting.extent.ExtentManager;

public class CucumberExtentManager {

    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();

    public static void init() {
        if (extent == null) {
            extent = ExtentManager.getReportObject();
        }
    }
    public static ExtentTest getTest() {
        return scenarioTest.get();
    }
    public static void startScenario(String scenarioName) {

        init();
        scenarioTest.set(extent.createTest(scenarioName));
    }

    public static void flush() {

        if (extent != null) {
            extent.flush();
        } else {
        }
    }
}