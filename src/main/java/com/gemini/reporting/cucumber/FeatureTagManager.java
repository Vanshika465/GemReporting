package com.gemini.reporting.cucumber;

import java.io.BufferedReader;
import java.io.FileReader;

public class FeatureTagManager {

    public static String getFeatureReportType(String featureFilePath) {

        try (BufferedReader br = new BufferedReader(
                new FileReader(featureFilePath))) {

            String line;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("Feature:")) {
                    break;
                }

                if (line.contains("@allure")) {
                    return "allure";
                }

                if (line.contains("@extent")) {
                    return "extent";
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "extent";
    }
}