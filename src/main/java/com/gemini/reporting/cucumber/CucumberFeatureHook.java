package com.gemini.reporting.cucumber;

import com.gemini.reporting.context.ReportingContext;
import com.gemini.reporting.control.ReportControl;
import com.gemini.reporting.extent.CucumberExtentManager;
import com.gemini.reporting.manager.ReportManager;
import com.gemini.reporting.summary.ExecutionSummaryManager;
import com.gemini.reporting.utils.APIAttachmentUtil;
import com.gemini.reporting.utils.AllureUtil;
import com.gemini.reporting.utils.ScreenshotUtil;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;

import java.nio.file.Paths;
import java.util.Base64;

public class CucumberFeatureHook {

    @Before(order = 0)
    public void beforeScenario(Scenario scenario) {

        try {

            String featurePath = Paths.get(scenario.getUri()).toString();

            String cliReportType = System.getProperty("reportType");

            String reportType;

            if (cliReportType != null && !cliReportType.isBlank()) {
                reportType = cliReportType;
            } else {
                reportType = FeatureTagManager.getFeatureReportType(featurePath);
            }

            if (reportType == null) {
                reportType = "extent";
            }

            ReportControl.set(reportType);

            ReportManager.reset();
            ReportManager.initialize(reportType);

            // -------- Execution Summary --------

            ExecutionSummaryManager.setFramework("Cucumber");

            if ("extent".equalsIgnoreCase(reportType)) {
                CucumberExtentManager.startScenario(scenario.getName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After(order = 100)
    public void afterScenario(Scenario scenario) {

        String reportType = ReportControl.get();

        WebDriver driver = ReportingContext.getDriver();

        String apiLog = ReportingContext.getApiLog();

        byte[] screenshot = null;

        if (scenario.isFailed() && driver != null) {
            screenshot = ScreenshotUtil.getScreenshotBytes(driver);
        }

        // ---------------- EXTENT ----------------

        if ("extent".equalsIgnoreCase(reportType)) {

            var test = CucumberExtentManager.getTest();

            if (test != null) {

                if (scenario.isFailed()) {

                    test.fail("Scenario Failed: " + scenario.getName());

                    if (screenshot != null) {

                        String base64 =
                                Base64.getEncoder().encodeToString(screenshot);

                        test.addScreenCaptureFromBase64String(base64);
                    }

                    if (apiLog != null) {
                        test.info("<pre>" + apiLog + "</pre>");
                    }

                } else {

                    test.pass("Scenario Passed: " + scenario.getName());

                }

                CucumberExtentManager.flush();
            }
        }

        // ---------------- ALLURE ----------------

        else if ("allure".equalsIgnoreCase(reportType)) {

            if (scenario.isFailed()) {

                if (screenshot != null) {
                    AllureUtil.attachScreenshot(screenshot);
                }

                if (apiLog != null) {
                    APIAttachmentUtil.attachApiLog(apiLog);
                }
            }
        }

        // -------- Execution Summary Counters --------

        if (scenario.isFailed()) {
            ExecutionSummaryManager.testFailed();
        } else {
            ExecutionSummaryManager.testPassed();
        }

        ReportingContext.unload();
    }
}