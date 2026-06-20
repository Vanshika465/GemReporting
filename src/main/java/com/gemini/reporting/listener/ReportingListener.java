package com.gemini.reporting.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.gemini.reporting.context.ReportingContext;
import com.gemini.reporting.extent.ExtentManager;
import com.gemini.reporting.utils.AllureUtil;
import com.gemini.reporting.utils.PlaywrightUtil;
import com.gemini.reporting.utils.ScreenshotUtil;
import com.gemini.reporting.utils.APIAttachmentUtil;

//import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.util.Base64;

public class ReportingListener implements ITestListener {

    private ExtentReports extent;
    private final ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private String reportType;
    private boolean isAllure;
    private boolean isExtent;
    @Override
    public void onStart(ITestContext context) {

        reportType = System.getProperty("reportType", "extent");

        isExtent = "extent".equalsIgnoreCase(reportType);
        isAllure = "allure".equalsIgnoreCase(reportType);

        if (isExtent) {
            extent = ExtentManager.getReportObject();
        }

        System.out.println("Report Type = " + reportType);
    }

    @Override
    public void onTestStart(ITestResult result) {
        if ("extent".equalsIgnoreCase(reportType)) {
            ExtentTest extentTest =
                    extent.createTest(result.getMethod().getMethodName());
            test.set(extentTest);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if ("extent".equalsIgnoreCase(reportType) && test.get() != null) {
            test.get().pass("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {

        WebDriver driver = ReportingContext.getDriver();
        String apiLog = ReportingContext.getApiLog();

        // =========================
        // SELENIUM FLOW
        // =========================
        if (driver != null) {

            byte[] screenshot =
                    ScreenshotUtil.getScreenshotBytes(driver);

            // EXTENT MODE
            if (isExtent && test.get() != null) {
                test.get().fail(result.getThrowable());

                String base64 =
                        Base64.getEncoder().encodeToString(screenshot);

                test.get().addScreenCaptureFromBase64String(base64);
            }

            // ALLURE MODE
            if (isAllure && screenshot != null) {
                AllureUtil.attachScreenshot(screenshot);
            }

            return;
        }

        // =========================
        // REST ASSURED FLOW
        // =========================
        if (apiLog != null) {

            if (isExtent && test.get() != null) {
                test.get().fail(result.getThrowable());
                test.get().info(apiLog);
            }

            if (isAllure) {
                APIAttachmentUtil.attachApiLog(apiLog);
            }

            return;
        }

        System.out.println("No Selenium driver or API log found");

// PLAYWRIGHT FLOW
        byte[] pwScreenshot = ReportingContext.getPwScreenshot();

        if (pwScreenshot != null) {

            if (isExtent && test.get() != null) {
                test.get().fail(result.getThrowable());

                String base64 =
                        Base64.getEncoder().encodeToString(pwScreenshot);

                test.get().addScreenCaptureFromBase64String(base64);
            }

            if (isAllure) {
                PlaywrightUtil.attachScreenshot(pwScreenshot);
            }

            return;
        }

    }


    @Override
    public void onFinish(ITestContext context) {
        if ("extent".equalsIgnoreCase(reportType) && extent != null) {
            extent.flush();
        }
    }
}