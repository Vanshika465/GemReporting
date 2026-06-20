````md
# Gemini Reporting SDK

A unified, plug-and-play **test reporting framework** that supports multiple automation stacks including:

- Selenium WebDriver UI Automation
- REST Assured API Automation
- Playwright (UI Automation support)
- TestNG-based test execution

This SDK is designed as a **dependency-based reporting solution**, which can be directly imported into any Maven project to enable **Extent Reports + Allure Reports with screenshots and logs automatically**.

---

# Purpose

This SDK eliminates the need to manually configure reporting in every automation project.

Instead of writing reporting logic again and again, you simply add this dependency:

```xml
<dependency>
    <groupId>com.gemini</groupId>
    <artifactId>gemini-reporting-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
````

and the SDK handles everything automatically.

---

# Key Features

## Multi-Framework Support

* Selenium WebDriver automation
* REST Assured API automation
* Playwright automation (screenshot support)

---

## Reporting Support

### Extent Reports

* Test execution report
* Step status (PASS/FAIL)
* Screenshot embedding (Base64)

### Allure Reports

* Failure screenshots as attachments
* API logs as attachments
* Execution metadata support

---

## Smart Failure Capture

On test failure, SDK automatically captures:

### Selenium:

* Browser screenshot

### API:

* Request details
* Response body
* Status code

### Playwright:

* Full page screenshot

---

## Dynamic Reporting Switch

You can switch reporting types using Maven profiles:

```bash
mvn clean test -Pextent
mvn clean test -Pallure
```

---

# Architecture Overview

The SDK is built using:

* `ITestListener` (TestNG Listener)
* ThreadLocal Context Management
* Extent Report Engine
* Allure Attachment API

---

# How It Works

### 1. Test Execution starts

SDK listener initializes reporting engine based on profile

### 2. Test runs

Framework-specific context is stored:

* WebDriver (Selenium)
* API logs (Rest Assured)
* Screenshots (Playwright)

### 3. On Failure

SDK automatically captures:

* Screenshot OR API logs
* Attaches to Extent and Allure

### 4. Report generation

Reports are generated automatically after execution.

---

# Supported Usage Examples

---

## Selenium Example

```
ReportingContext.setDriver(driver);
```

---

## REST Assured Example

```
ReportingContext.setApiLog(apiLog);
```

---

## Playwright Example

```
ReportingContext.setPwScreenshot(page.screenshot());
```

---

# How to Use in Any Project

## Step 1: Add Dependency

```xml
<dependency>
    <groupId>com.gemini</groupId>
    <artifactId>gemini-reporting-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## Step 2: Add Listener

```
@Listeners(com.gemini.reporting.listener.ReportingListener.class)
```

OR add in testng.xml:

```xml
<listener class-name="com.gemini.reporting.listener.ReportingListener"/>
```

---

## Step 3: Run Tests

### Extent Report

```bash
mvn clean test -Pextent
```

### Allure Report

```bash
mvn clean test -Pallure
```

---

# Output Reports

## Extent

```
/test-output/ExtentReport.html
```

## Allure

```
/allure-results
```

Run:

```bash
allure serve allure-results
```

---

# Advantages

* No reporting code needed in tests
* Plug-and-play dependency
* Works across multiple frameworks
* Centralized reporting logic
* Easy integration in enterprise projects

---

# Note

This is a **dependency-based reporting SDK**.
Just include it in any Maven project and start getting unified reports instantly.

```
