package api.utilities;

import java.io.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class XLUtilities {

    private String path;

    public XLUtilities(String path) {
        this.path = path;
    }

    public int getRowCount(String sheetName) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
        		XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Sheet '" + sheetName + "' not found.");
            return sheet.getLastRowNum();
        }
    }

    public int getCellCount(String sheetName, int rownum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path);
        		XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Sheet '" + sheetName + "' not found.");
            XSSFRow row = sheet.getRow(rownum);
            if (row == null) return 0;
            return row.getLastCellNum();
        }
    }

    public String getCellData(String sheetName, int rownum, int colnum) throws IOException {
        try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Sheet '" + sheetName + "' not found.");
            XSSFRow row = sheet.getRow(rownum);
            if (row == null) return "";
            XSSFCell cell = row.getCell(colnum);
            if (cell == null) return "";
            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);
        }
    }

    public void setCellData(String sheetName, int rownum, int colnum, String data) throws IOException {
        try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) sheet = workbook.createSheet(sheetName);
            XSSFRow row = sheet.getRow(rownum);
            if (row == null) row = sheet.createRow(rownum);
            XSSFCell cell = row.createCell(colnum);
            cell.setCellValue(data);
            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }

    public void fillGreenColor(String sheetName, int rownum, int colnum) throws IOException {
        setCellColor(sheetName, rownum, colnum, IndexedColors.GREEN);
    }

    public void fillRedColor(String sheetName, int rownum, int colnum) throws IOException {
        setCellColor(sheetName, rownum, colnum, IndexedColors.RED);
    }

    private void setCellColor(String sheetName, int rownum, int colnum, IndexedColors color) throws IOException {
        try (FileInputStream fi = new FileInputStream(path); XSSFWorkbook workbook = new XSSFWorkbook(fi)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) throw new IOException("Sheet '" + sheetName + "' not found.");
            XSSFRow row = sheet.getRow(rownum);
            if (row == null) throw new IOException("Row " + rownum + " not found in sheet '" + sheetName + "'.");
            XSSFCell cell = row.getCell(colnum);
            if (cell == null) throw new IOException("Cell at row " + rownum + " and column " + colnum + " not found.");
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(color.getIndex());
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            try (FileOutputStream fo = new FileOutputStream(path)) {
                workbook.write(fo);
            }
        }
    }
}
