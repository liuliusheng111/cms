package com.jflyfox.common.utils;


import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * @author: yonghui.chen
 * @created: 15/4/27
 */
public class XlsUtils {

    public static void export(OutputStream out, String sheetName,
                              String[] names, List<List<String>> rows) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        exportSheet(workbook, sheetName, names, rows);

        workbook.write(out);
        out.close();
    }

    public static void exportSheet(XSSFWorkbook workbook, String sheetName, String[] names, List<List<String>> rows) {
        XSSFSheet sheet = workbook.createSheet(sheetName);

        int i = 0;
        int rowNum = 0;
        XSSFRow row = sheet.createRow(rowNum++);
        for (String name : names) {
            XSSFCell cell = row.createCell(i++);
            cell.setCellValue(name);
        }

        for (List<String> stringList : rows) {
            row = sheet.createRow(rowNum++);
            i = 0;
            for (String s : stringList) {
                XSSFCell cell = row.createCell(i++);
                cell.setCellValue(s);
            }
        }
    }
}
