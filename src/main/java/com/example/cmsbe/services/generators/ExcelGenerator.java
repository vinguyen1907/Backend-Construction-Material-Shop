package com.example.cmsbe.services.generators;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public abstract class ExcelGenerator extends Generator {
    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;

    public ExcelGenerator() {
        workbook = new XSSFWorkbook();
    }

    protected void createNewSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
    }

    protected XSSFRow createRow(int rowNum) {
        return sheet.createRow(rowNum);
    }

    protected abstract void writeHeaderLine();
    protected abstract void writeDataLines();
}
