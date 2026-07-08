 package com.gemini.reporting.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.gemini.reporting.context.ReportingContext;
import com.gemini.reporting.control.ReportControl;
import com.gemini.reporting.manager.ReportManager;
import com.gemini.reporting.utils.APIAttachmentUtil;
import com.gemini.reporting.utils.AllureUtil;
import com.gemini.reporting.utils.PlaywrightUtil;
import com.gemini.reporting.utils.ScreenshotUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Base64;

public class ReportingListener implements ITestListener {

    private ExtentReports extent;
    private final ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    private static final Logger logger =
            LogManager.getLogger(ReportingListener.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("Execution Started");

        // For old TestNG projects
        // Cucumber projects will initialize inside Cucumber hook
        String cmdType = System.getProperty("reportType");

        if (ReportManager.getReportType() == null) {
            ReportManager.initialize(cmdType);
        }
        ReportControl.set(ReportManager.getReportType());
    }

    @Override
    public void onTestStart(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if ("runScenario".equals(testName)) {
            return;
        }


        if (ReportManager.isExtent()) {
            ReportControl.set("extent");

            extent = ReportManager.getExtent();

            if (extent != null) {
                ExtentTest extentTest = extent.createTest(testName);

                ReportManager.setCurrentTest(extentTest);
                test.set(extentTest);
            }
        }
        else if (ReportManager.isAllure()) {
            ReportControl.set("allure");
        }

        logger.info("Test Started: {}", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        if ("runScenario".equals(testName)) {
            return;
        }

        if (ReportManager.isExtent() && test.get() != null) {
            test.get().pass("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();
        logger.error("Test Failed: {}", testName);

        if (result.getThrowable() != null) {
            logger.error(result.getThrowable().getMessage(), result.getThrowable());
        }

        if ("runScenario".equals(testName)) {
            return;
        }

        if (ReportManager.isExtent() && test.get() != null) {
            Throwable error = result.getThrowable();

            if (ReportManager.isExtent() && test.get() != null) {
                test.get().fail("Test Failed: " + testName);

                if (error != null) {
                    test.get().fail(error.getMessage());
                    test.get().fail(error);
                }
            }
        }

        WebDriver driver = ReportingContext.getDriver();
        String apiLog = ReportingContext.getApiLog();
        byte[] pwScreenshot = ReportingContext.getPwScreenshot();

        // Selenium
        if (driver != null) {
            byte[] screenshot = ScreenshotUtil.getScreenshotBytes(driver);

            if (screenshot != null) {
                if (ReportManager.isExtent() && test.get() != null) {
                    String base64 = Base64.getEncoder()
                            .encodeToString(screenshot);
                    test.get().addScreenCaptureFromBase64String(base64);
                }

                if (ReportManager.isAllure()) {
                    AllureUtil.attachScreenshot(screenshot);
                }
            }
            return;
        }

        // API
        if (apiLog != null) {
            if (ReportManager.isExtent() && test.get() != null) {
                test.get().info(apiLog);
            }

            if (ReportManager.isAllure()) {
                APIAttachmentUtil.attachApiLog(apiLog);
            }
            return;
        }

        // Playwright
        if (pwScreenshot != null) {
            if (ReportManager.isExtent() && test.get() != null) {
                String base64 = Base64.getEncoder()
                        .encodeToString(pwScreenshot);
                test.get().addScreenCaptureFromBase64String(base64);
            }

            if (ReportManager.isAllure()) {
                PlaywrightUtil.attachScreenshot(pwScreenshot);
            }
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        // Flush only for normal TestNG extent runs
        if (ReportManager.isExtent()
                && ReportManager.getExtent() != null) {
            ReportManager.getExtent().flush();
        }

        ReportManager.reset();
        logger.info("Execution completed");
    }
}