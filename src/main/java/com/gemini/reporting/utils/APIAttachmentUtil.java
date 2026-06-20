package com.gemini.reporting.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

public class APIAttachmentUtil {

    public static void attachApiLog(String apiLog) {

        if (apiLog == null) return;

        Allure.addAttachment(
                "API Failure Details",
                "text/plain",
                new ByteArrayInputStream(
                        apiLog.getBytes(StandardCharsets.UTF_8)
                ),
                "txt"
        );
    }
}