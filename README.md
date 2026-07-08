# GemReport

A plug-and-play reporting framework for automation projects that provides unified reporting across multiple testing frameworks.

Supports:

* Selenium WebDriver (UI)
* REST Assured (API)
* Playwright
* TestNG
* Cucumber

GemReport automatically generates **Extent Reports** and **Allure Reports** with screenshots, API logs, and execution logs.

---

# Purpose

Automation projects often require repeated setup for:

* Reporting
* Screenshot capture
* Failure handling
* API attachments
* Logging

GemReport removes this repeated effort.

Just add the SDK dependency and integrate minimal setup in your project.

---

# Key Features

## Multi-Framework Support

Works with:

* Selenium UI automation
* API automation
* Playwright automation
* TestNG
* Cucumber

---

## Extent Reports

Provides:

* PASS / FAIL status
* Step logs
* Failure screenshots
* API logs
* Exception details

Example logs:

* INFO: Opening login page
* PASS: Login successful
* FAIL: Username validation failed

---

## Allure Reports

Provides:

* Screenshot attachments
* API request/response attachments
* Failure details
* Step-level execution logs

Generate report using:

```bash id="k5b2ys"
allure serve allure-results
```

---

# Smart Failure Capture

On failure, SDK automatically captures relevant evidence.

## Selenium

* Browser screenshot

## API

* Request details
* Response body
* Status code

## Playwright

* Full-page screenshot

---

# Report Switching

GemReport supports two switching mechanisms.

---

## 1. Command Line Switching (TestNG / Normal Projects)

Run Extent:

```bash id="ewcklj"
mvn clean test -Pextent
```

Run Allure:

```bash id="l9jlwm"
mvn clean test -Pallure
```

OR

```bash id="1ts2x0"
mvn clean test -DreportType=extent
```

```bash id="s7jtrv"
mvn clean test -DreportType=allure
```

Best for:

* Selenium TestNG projects
* API TestNG projects
* Playwright projects

---

## 2. Feature Tag Switching (Cucumber)

Each feature file can choose its report type.

Example:

### UI Feature

```gherkin id="e7vt8e"
@allure
Feature: UI Testing
```

### API Feature

```gherkin id="f4ncb1"
@extent
Feature: API Testing
```

SDK reads the feature tag and generates the correct report.

---

# Dynamic Runner for Cucumber

Normal Cucumber runner could not switch reporting plugins dynamically per feature.

Example problem:

```gherkin id="3x33cx"
ui.feature -> @allure
api.feature -> @extent
```

Normal runner uses one plugin for the whole execution.

To solve this, GemReport uses a **Dynamic Runner**.

Dynamic Runner:

* Reads feature files one by one
* Detects feature tags
* Chooses report type
* Runs features separately

This enables:

* UI feature → Allure
* API feature → Extent

in the same run.

---

# Consumer Setup

## Step 1 — Add Dependency

```xml id="83mykj"
<dependency>
    <groupId>com.gemini</groupId>
    <artifactId>gemini-reporting-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Step 2 — Add Listener (TestNG Projects)

Using annotation:


@Listeners(com.gemini.reporting.listener.ReportingListener.class)
```

OR in `testng.xml`:

```xml id="7se6w8"
<listener class-name="com.gemini.reporting.listener.ReportingListener"/>
```

---

## Step 3 — Provide Runtime Context

### Selenium


ReportingContext.setDriver(driver);


### API

ReportingContext.setApiLog(apiLog);


### Playwright

ReportingContext.setPwScreenshot(bytes);


---

## Step 4 — Add Logs (Optional)

ReportLogger.info("Opening browser");
ReportLogger.pass("Login successful");
ReportLogger.fail("Validation failed");


Logs appear in both:

* Extent
* Allure

---

# Cucumber Setup

For Cucumber consumer projects:

Add:

* Feature files
* Step definitions
* Hooks
* Dynamic runner

Required glue:

stepdefinitions
hooks
com.gemini.reporting.cucumber


---

# Output Reports

## Extent Report

```text id="u3iz1d"
/test-output/ExtentReport.html
```

## Allure Results

```text id="g7d5h8"
/allure-results
```

View report:

```bash id="uljlwm"
allure serve allure-results
```

---

# Advantages

* Plug-and-play
* Minimal integration effort
* Multi-framework support
* Centralized reporting
* Rich failure evidence
* Command-line switching
* Cucumber tag-based switching

---

# Summary

GemReport provides a unified reporting solution for:

* Selenium
* API
* Playwright
* TestNG
* Cucumber

with:

* Extent Reports
* Allure Reports
* Screenshots
* API Attachments
* Execution Logs
* Dynamic Report Switching
