package com.gemini.reporting.utils;

import com.aventstack.extentreports.ExtentTest;
import com.gemini.reporting.control.ReportControl;
import com.gemini.reporting.extent.CucumberExtentManager;
import com.gemini.reporting.manager.ReportManager;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReportLogger {

    private static final Logger logger =
            LogManager.getLogger(ReportLogger.class);

    private static ExtentTest getExtentTest() {

        // TestNG Flow
        ExtentTest test = ReportManager.getCurrentTest();

        if (test != null) {
            return test;
        }

        // Cucumber Flow
        return CucumberExtentManager.getTest();
    }

    public static void info(String message) {

        logger.info(message);

        String reportType = ReportControl.get();
        ExtentTest test = getExtentTest();

        if ("extent".equalsIgnoreCase(reportType) && test != null) {
            test.info(message);
        }
        else if ("allure".equalsIgnoreCase(reportType)) {
            Allure.step("INFO : " + message);
        }
    }

    public static void pass(String message) {

        logger.info("PASS : {}", message);

        String reportType = ReportControl.get();
        ExtentTest test = getExtentTest();

        if ("extent".equalsIgnoreCase(reportType) && test != null) {
            test.pass(message);
        }
        else if ("allure".equalsIgnoreCase(reportType)) {
            Allure.step("PASS : " + message);
        }
    }

    public static void fail(String message) {

        logger.error("FAIL : {}", message);

        String reportType = ReportControl.get();
        ExtentTest test = getExtentTest();

        if ("extent".equalsIgnoreCase(reportType) && test != null) {
            test.fail(message);
        }
        else if ("allure".equalsIgnoreCase(reportType)) {
            Allure.step("FAIL : " + message);
            Allure.addAttachment("Failure", message);
        }
    }
}