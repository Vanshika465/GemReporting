package com.gemini.reporting.excel;

import com.gemini.reporting.summary.ExecutionSummaryManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.time.Duration;

public class ExecutionSummaryExcelGenerator {

    public static void generate() {

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Execution Summary");

        // Header Style
        CellStyle headerStyle = workbook.createCellStyle();

        Font headerFont = workbook.createFont();

        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);

        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);

        // Value Style
        CellStyle valueStyle = workbook.createCellStyle();

        valueStyle.setBorderTop(BorderStyle.THIN);
        valueStyle.setBorderBottom(BorderStyle.THIN);
        valueStyle.setBorderLeft(BorderStyle.THIN);
        valueStyle.setBorderRight(BorderStyle.THIN);

        Duration duration = Duration.between(
                ExecutionSummaryManager.getStartTime(),
                ExecutionSummaryManager.getEndTime());

        long seconds = duration.toSeconds();

        long minutes = seconds / 60;

        seconds = seconds % 60;

        double passPercentage = 0;

        if (ExecutionSummaryManager.getTotalTests() != 0) {

            passPercentage =
                    (ExecutionSummaryManager.getPassedTests() * 100.0)
                            / ExecutionSummaryManager.getTotalTests();
        }

        int rowNum = 0;

        createRow(sheet, rowNum++, "Framework",
                ExecutionSummaryManager.getFramework(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Report Type",
                ExecutionSummaryManager.getReportType(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Execution Date",
                ExecutionSummaryManager.getStartTime().toLocalDate().toString(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Start Time",
                ExecutionSummaryManager.getStartTime().toLocalTime().toString(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "End Time",
                ExecutionSummaryManager.getEndTime().toLocalTime().toString(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Duration",
                minutes + " min " + seconds + " sec",
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Operating System",
                ExecutionSummaryManager.getOs(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Java Version",
                ExecutionSummaryManager.getJavaVersion(),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Total Tests",
                String.valueOf(ExecutionSummaryManager.getTotalTests()),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Passed",
                String.valueOf(ExecutionSummaryManager.getPassedTests()),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Failed",
                String.valueOf(ExecutionSummaryManager.getFailedTests()),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Skipped",
                String.valueOf(ExecutionSummaryManager.getSkippedTests()),
                headerStyle, valueStyle);

        createRow(sheet, rowNum++, "Pass Percentage",
                String.format("%.2f %%", passPercentage),
                headerStyle, valueStyle);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);

        try {

            File folder = new File("reports");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            FileOutputStream fos =
                    new FileOutputStream("reports/ExecutionSummary.xlsx");

            workbook.write(fos);

            fos.close();

            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createRow(
            Sheet sheet,
            int rowNum,
            String key,
            String value,
            CellStyle headerStyle,
            CellStyle valueStyle) {

        Row row = sheet.createRow(rowNum);

        Cell keyCell = row.createCell(0);

        keyCell.setCellValue(key);

        keyCell.setCellStyle(headerStyle);

        Cell valueCell = row.createCell(1);

        valueCell.setCellValue(value);

        valueCell.setCellStyle(valueStyle);
    }
}