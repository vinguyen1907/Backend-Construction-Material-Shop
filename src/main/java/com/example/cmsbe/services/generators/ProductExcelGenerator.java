package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.Product;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ProductExcelGenerator extends ExcelGenerator {
    private final List<Product> products;

    public ProductExcelGenerator(List<Product> products) {
        this.products = products;
    }

    @Override
    protected void writeHeaderLine() {
        super.createNewSheet("Products");

        Row row = super.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Name", style);
        createCell(row, 1, "Origin", style);
        createCell(row, 2, "Description", style);
        createCell(row, 3, "Unit price", style);
        createCell(row, 4, "Calculation unit", style);
        createCell(row, 5, "Sold quantity", style);
        createCell(row, 6, "Remaining quantity", style);
    }

    @Override
    protected void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Product product : products) {
            Row row = super.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, product.getName(), style);
            sheet.autoSizeColumn(0);
            createCell(row, columnCount++, product.getOrigin(), style);
            sheet.autoSizeColumn(1);
            createCell(row, columnCount++, product.getDescription(), style);
            sheet.autoSizeColumn(2);
            createCell(row, columnCount++, String.valueOf(product.getUnitPrice()), style);
            sheet.autoSizeColumn(3);
            createCell(row, columnCount++, product.getCalculationUnit(), style);
            sheet.autoSizeColumn(4);
            createCell(row, columnCount++, String.valueOf(product.getQuantitySold()), style);
            sheet.autoSizeColumn(5);
            createCell(row, columnCount, String.valueOf(product.getQuantityRemaining()), style);
            sheet.autoSizeColumn(6);
        }
    }
}
