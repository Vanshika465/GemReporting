package com.company.reporting.extent;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getReportObject() {

        if (extent == null) {

            String reportDir = System.getProperty("user.dir")
                    + File.separator + "reports";

            String reportPath = reportDir
                    + File.separator + "ExtentReport.html";

            // Create folder if not exists
            new File(reportDir).mkdirs();

            ExtentSparkReporter reporter =
                    new ExtentSparkReporter(reportPath);

            // =========================
            // REPORT CONFIGURATION
            // =========================
            reporter.config().setReportName("UI Automation Report");
            reporter.config().setDocumentTitle("GreenKart Automation Results");
            reporter.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);

            // =========================
            // EXTENT INSTANCE
            // =========================
            extent = new ExtentReports();
            extent.attachReporter(reporter);

            // =========================
            // SYSTEM INFO (USEFUL IN INTERVIEWS)
            // =========================
            extent.setSystemInfo("Tester", "Vanshika");
            extent.setSystemInfo("Framework", "Selenium + TestNG");
            extent.setSystemInfo("Language", "Java");
            extent.setSystemInfo("Execution Type", "Automated UI Testing");
            extent.setSystemInfo("OS", System.getProperty("os.name"));
        }

        return extent;
    }
}