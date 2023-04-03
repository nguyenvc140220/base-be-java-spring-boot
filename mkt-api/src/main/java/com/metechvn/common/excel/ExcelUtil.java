package com.metechvn.common.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;

public class ExcelUtil {
    public static ByteArrayInputStream toExcel(List<? extends IExcelSerializable> data) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            if (data != null && !data.isEmpty()) {
                List<String> header = data.get(0).getHeader();
                Sheet sheet = workbook.createSheet();

                // Header
                Row headerRow = sheet.createRow(0);
                Cell cell = headerRow.createCell(0);
                cell.setCellValue("STT");

                for (int col = 0; col < header.size(); col++) {
                    cell = headerRow.createCell(col + 1);
                    cell.setCellValue(header.get(col));
                }

                int rowIdx = 1;
                for (IExcelSerializable mst : data) {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(rowIdx - 1);

                    for (int i = 0; i < mst.getValues().size(); i++) {
                        row.createCell(i + 1).setCellValue(mst.getValues().get(i));
                    }
                }
                IntStream.range(0, header.size()).forEach(sheet::autoSizeColumn);
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }
            throw new RuntimeException("fail to import data to Excel file: No Data");
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
        }
    }
}
