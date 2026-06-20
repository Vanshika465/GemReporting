package com.gemini.reporting.extent;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.util.Base64;

public class ExtentManager {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    // ---------------- INIT ----------------
    public static ExtentReports getReportObject() {

        if (extent == null) {

            String reportDir = System.getProperty("user.dir") + "/reports";
            new File(reportDir).mkdirs();

            String path = reportDir + "/ExtentReport.html";

            ExtentSparkReporter reporter = new ExtentSparkReporter(path);

            reporter.config().setReportName("UI Automation Report");
            reporter.config().setDocumentTitle("Automation Results");

            extent = new ExtentReports();
            extent.attachReporter(reporter);

            extent.setSystemInfo("Tester", "Vanshika");
        }

        return extent;
    }

    // ---------------- CREATE TEST ----------------
    public static void createTest(String testName) {
        ExtentTest extentTest = getReportObject().createTest(testName);
        test.set(extentTest);
    }

    // ---------------- PASS ----------------
    public static void passTest(String testName) {
        if (test.get() != null) {
            test.get().pass(testName + " PASSED");
        }
    }

    // ---------------- FAIL ----------------
    public static void failTest(String testName, Throwable error, byte[] screenshot) {

        if (test.get() != null) {

            test.get().fail(error);

            String base64 = Base64.getEncoder().encodeToString(screenshot);

            test.get().addScreenCaptureFromBase64String(base64);
        }
    }

    // ---------------- FLUSH ----------------
    public static void flush() {
        if (extent != null) {
            extent.flush();
        }
    }
}