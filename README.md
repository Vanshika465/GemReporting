# GemReport

A plug-and-play reporting framework for automation projects that provides a unified reporting solution across multiple testing frameworks.

## Supported Technologies

- Selenium WebDriver (UI)
- REST Assured (API)
- Playwright
- TestNG
- Cucumber

GemReport automatically generates **Extent Reports** and **Allure Reports** with screenshots, API logs, execution logs, and rich failure evidence.

---

# Purpose

Automation projects often require repeated implementation for:

- Reporting
- Screenshot capture
- Failure handling
- API attachments
- Logging

GemReport eliminates this repeated effort by providing a reusable SDK.

Simply add the dependency, perform minimal integration, and start generating rich reports.

---

# Key Features

## Multi-Framework Support

GemReport works with:

- Selenium UI Automation
- REST Assured API Automation
- Playwright Automation
- TestNG
- Cucumber

---

## Extent Reports

Provides:

- PASS / FAIL status
- Step-level logs
- Failure screenshots
- API logs
- Exception details
- Execution logs

Example logs:

- INFO: Launching browser
- INFO: Opening Login page
- PASS: Login successful
- FAIL: Username validation failed

---

## Allure Reports

Provides:

- Screenshot attachments
- API request/response attachments
- Failure details
- Step-level execution logs

Generate report using:

```bash
allure serve allure-results
```

---

# Smart Failure Capture

On failure, GemReport automatically captures relevant evidence.

## Selenium

- Browser screenshot

## API

- Request details
- Response body
- Status code

## Playwright

- Full-page screenshot

---

# Report Switching

GemReport supports two report switching mechanisms.

---

## 1. Command-Line Switching (TestNG / Standard Projects)

Run Extent Report:

```bash
mvn clean test -Pextent
```

Run Allure Report:

```bash
mvn clean test -Pallure
```

or

```bash
mvn clean test -DreportType=extent
```

```bash
mvn clean test -DreportType=allure
```

Best suited for:

- Selenium TestNG Projects
- REST Assured Projects
- Playwright Projects

---

## 2. Feature-Level Switching (Cucumber)

Each feature file controls its own report type.

Example:

### UI Feature

```gherkin
@allure
Feature: UI Testing
```

### API Feature

```gherkin
@extent
Feature: API Testing
```

GemReport reads the feature tag before execution and initializes the appropriate reporting engine automatically.

> **Note:** Report selection is supported only at the **Feature level**. Scenario-level report tags are intentionally ignored to ensure a consistent reporting configuration for all scenarios within a feature.

---

# Dynamic Runner for Cucumber

Standard Cucumber runners initialize reporting plugins only once for the entire execution.

Example:

```
ui.feature   -> @allure
api.feature  -> @extent
```

A normal runner cannot switch reporting engines between features.

To overcome this limitation, GemReport provides a **Dynamic Runner**.

The Dynamic Runner:

- Reads feature files individually
- Detects feature-level report tags
- Initializes the appropriate reporting engine
- Executes the feature
- Flushes the generated report
- Repeats the process for remaining features

This enables:

- UI Feature → Allure Report
- API Feature → Extent Report

within the same execution.

---
## Version 1.1.0

### New Features

- Execution Summary Excel Report
- Execution Summary Text Report
- Better SDK initialization
- Improved Extent Report support

# Consumer Project Configuration

## Step 1 — Add GemReport Dependency

```xml
<dependency>
    <groupId>com.gemini</groupId>
    <artifactId>GemReport</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Step 2 — Configure TestNG Listener

Using annotation:

```
@Listeners(com.gemini.reporting.listener.ReportingListener.class)
```

or in `testng.xml`:

```xml
<listener class-name="com.gemini.reporting.listener.ReportingListener"/>
```

---

## Step 3 — Provide Runtime Context

### Selenium

```
ReportingContext.setDriver(driver);
```

### API

```
ReportingContext.setApiLog(apiLog);
```

### Playwright

```
ReportingContext.setPwScreenshot(bytes);
```

---

## Step 4 — Add Execution Logs (Optional)

```
ReportLogger.info("Opening browser");
ReportLogger.pass("Login successful");
ReportLogger.fail("Validation failed");
```

These logs appear automatically in:

- Extent Reports
- Allure Reports

---

# TestNG Consumer Requirements

To support profile-based report switching, the consumer project's `pom.xml` must define reporting profiles.

Example:

```xml
<profiles>

    <profile>
        <id>extent</id>
        <properties>
            <reportType>extent</reportType>
        </properties>
    </profile>

    <profile>
        <id>allure</id>
        <properties>
            <reportType>allure</reportType>
        </properties>
    </profile>

</profiles>
```

This enables execution using:

```bash
mvn clean test -Pextent
```

or

```bash
mvn clean test -Pallure
```

Without these profiles, profile-based report switching is not available.

---

# Cucumber Consumer Requirements

Cucumber consumer projects require:

- Feature Files
- Step Definitions
- Consumer Hooks
- GemReport Cucumber Hooks
- GemReport Dynamic Runner

Required glue:

```
stepdefinitions
hooks
com.gemini.reporting.cucumber
```

The Dynamic Runner is mandatory for feature-level report switching.

---

# Output Reports

## Extent Report

```
test-output/ExtentReport.html
```

---

## Allure Results

```
allure-results/
```

View the report using:

```bash
allure serve allure-results
```

---

# Prerequisites

- Java 21 or later
- Maven 3.9 or later
- Allure CLI (required only for viewing Allure reports)
- TestNG consumer projects must configure Maven reporting profiles
- Cucumber consumer projects must use the GemReport Dynamic Runner

---

# Advantages

- Plug-and-play integration
- Multi-framework support
- Unified reporting solution
- Extent and Allure reporting
- Feature-level report switching for Cucumber
- Command-line report switching for TestNG
- Dynamic Cucumber Runner
- Step-level execution logs
- Automatic screenshot capture
- Automatic API log attachments
- Rich failure evidence
- Centralized reporting
- Minimal consumer configuration
- Easily reusable across automation projects

---

# Summary

GemReport provides a reusable, unified reporting SDK for:

- Selenium
- REST Assured
- Playwright
- TestNG
- Cucumber

with built-in support for:

- Extent Reports
- Allure Reports
- Execution Logs
- Browser Screenshots
- API Attachments
- Failure Evidence
- Dynamic Report Switching
- Feature-Level Report Selection
- Plug-and-Play Integration