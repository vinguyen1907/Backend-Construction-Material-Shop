package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.Product;
import com.example.cmsbe.services.interfaces.IUserService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class ProductExcelGenerator extends ExcelGenerator {
    private final List<Product> products;

    public ProductExcelGenerator(IUserService userService, List<Product> products) {
        super(userService);
        this.products = products;
    }

    @Override
    protected void writeTitleLine() {
        createNewSheet("Products");

        CellStyle titleStyle = createTitleStyle();

        Row sheetNameRow = createRow(titleRowNum);
        createCell(sheetNameRow, startColumn, "CMS PRODUCT REPORT", titleStyle);
        var mergedRegionStart = startColumn;
        var mergedRegionEnd = 8; /// todo:
        sheet.addMergedRegion(new CellRangeAddress(sheetNameRow.getRowNum(), sheetNameRow.getRowNum(), mergedRegionStart, mergedRegionEnd));
    }

    @Override
    protected void writeHeaderLine() {
        CellStyle style = createHeaderStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        Row row = super.createRow(headerRowNum);
        row.setHeight((short) (row.getHeight() * 1.5));

        createCell(row, startColumn, "No", style);
        createCell(row, startColumn + 1, "Name", style);
        createCell(row, startColumn + 2, "Origin", style);
        createCell(row, startColumn + 3, "Description", style);
        createCell(row, startColumn + 4, "Unit price", style);
        createCell(row, startColumn + 5, "Calculation unit", style);
        createCell(row, startColumn + 6, "Sold quantity", style);
        createCell(row, startColumn + 7, "Remaining quantity", style);
    }

    @Override
    protected void writeDataLines() {
        int rowCount = headerRowNum + 1;

        CellStyle style = createCellStyle();

        int index = 0;
        int columnCount = startColumn;
        for (Product product : products) {
            Row row = super.createRow(rowCount++);
            columnCount = startColumn;

            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, product.getName(), style);
            createCell(row, columnCount++, product.getOrigin(), style);
            createCell(row, columnCount++, product.getDescription(), style);
            createCell(row, columnCount++, product.getUnitPrice(), style);
            createCell(row, columnCount++, product.getCalculationUnit(), style);
            createCell(row, columnCount++, product.getQuantitySold(), style);
            createCell(row, columnCount, product.getQuantityRemaining(), style);
        }

        for (int i = startColumn + 1; i < columnCount + startColumn; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (int) (sheet.getColumnWidth(i) * 1.2)); // Adjust the padding factor as needed
        }
    }
}
