package com.gemini.reporting.cucumber;

import com.gemini.reporting.summary.ExecutionSummaryManager;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;

public class CucumberExecutionHook {

    @BeforeAll
    public static void beforeAll() {

        ExecutionSummaryManager.startExecution();

        ExecutionSummaryManager.setFramework("Cucumber");
    }

    @AfterAll
    public static void afterAll() {

        ExecutionSummaryManager.finishExecution();

        ExecutionSummaryManager.generateSummary();
    }
}