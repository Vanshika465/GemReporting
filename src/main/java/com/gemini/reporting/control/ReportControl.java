package com.gemini.reporting.control;

public class ReportControl {

    private static final ThreadLocal<String> reportType =
            new ThreadLocal<>();

    public static void set(String type) {
        reportType.set(type);
    }

    public static String get() {
        return reportType.get();
    }

    public static boolean isExtent() {
        return "extent".equalsIgnoreCase(get());
    }

    public static boolean isAllure() {
        return "allure".equalsIgnoreCase(get());
    }

    public static void clear() {
        reportType.remove();
    }
}