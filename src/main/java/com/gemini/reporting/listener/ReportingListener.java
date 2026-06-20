package com.gemini.reporting.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
//import com.company.reporting.allure.AllureHelper;
import com.gemini.reporting.context.ReportingContext;
import com.gemini.reporting.extent.ExtentManager;
import com.gemini.reporting.utils.AllureUtil;
import com.gemini.reporting.utils.ScreenshotUtil;
//import io.qameta.allure.Allure;
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

        if (driver == null) {
            System.out.println("Driver is null");
            return;
        }

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
    }

    @Override
    public void onFinish(ITestContext context) {
        if ("extent".equalsIgnoreCase(reportType) && extent != null) {
            extent.flush();
        }
    }
}