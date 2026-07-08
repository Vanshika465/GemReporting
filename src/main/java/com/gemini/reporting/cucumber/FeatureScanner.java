package com.gemini.reporting.cucumber;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FeatureScanner {

    public static String detectReportType(String featurePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(featurePath));

            for (String line : lines) {
                line = line.trim();

                if (line.startsWith("@")) {
                    if (line.contains("@allure")) {
                        return "allure";
                    }

                    if (line.contains("@extent")) {
                        return "extent";
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "extent";
    }
}