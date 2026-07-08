package com.gemini.reporting.manager;

import com.aventstack.extentreports.ExtentReports;
import com.gemini.reporting.extent.ExtentManager;
import com.aventstack.extentreports.ExtentTest;

public class ReportManager {

    private static String reportType;
    private static ExtentReports extent;
    private static final ThreadLocal<ExtentTest> currentTest =
            new ThreadLocal<>();

    public static synchronized void initialize(String type) {

        String newType =
                (type == null || type.isBlank()) ? "extent" : type;

        if (newType.equalsIgnoreCase(reportType)) {
            return;
        }

        reportType = newType;

        if ("extent".equalsIgnoreCase(reportType)) {
            extent = ExtentManager.getReportObject();
        } else {
            extent = null;
        }

        System.out.println("Report type = " + reportType);
    }

    public static boolean isExtent() {
        return "extent".equalsIgnoreCase(reportType);
    }

    public static boolean isAllure() {
        return "allure".equalsIgnoreCase(reportType);
    }

    public static ExtentReports getExtent() {
        return extent;
    }

    public static String getReportType() {
        return reportType;
    }
    public static void setCurrentTest(ExtentTest test) {
        currentTest.set(test);
    }

    public static ExtentTest getCurrentTest() {
        return currentTest.get();
    }

    public static void clearCurrentTest() {
        currentTest.remove();
    }

    public static void reset() {
        reportType = null;
        extent = null;
    }
}