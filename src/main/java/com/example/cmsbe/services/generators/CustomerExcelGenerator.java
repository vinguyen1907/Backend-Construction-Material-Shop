package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.services.interfaces.IUserService;
import com.example.cmsbe.utils.DecimalUtil;
import com.example.cmsbe.utils.OrderUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;

public class CustomerExcelGenerator extends ExcelGenerator {
    private final List<Customer> customers;

    public CustomerExcelGenerator(IUserService userService, List<Customer> customers) {
        super(userService);
        this.customers = customers;
    }

    @Override
    protected void writeTitleLine() {
        createNewSheet("Customers");

        CellStyle titleStyle = createTitleStyle();

        Row sheetNameRow = createRow(titleRowNum);
        createCell(sheetNameRow, startColumn, "CMS CUSTOMER REPORT", titleStyle);
        var mergedRegionStart = startColumn;
        var mergedRegionEnd = 8;
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
        createCell(row, startColumn + 2, "Phone", style);
        createCell(row, startColumn + 3, "Date of birth", style);
        createCell(row, startColumn + 4, "Contact address", style);
        createCell(row, startColumn + 5, "Orders", style);
        createCell(row, startColumn + 6, "Orders Value", style);
        createCell(row, startColumn + 7, "Debt", style);
    }

    @Override
    protected void writeDataLines() {
        int rowCount = headerRowNum + 1;

        CellStyle style = createCellStyle();

        int index = 0;
        int columnCount = startColumn;
        for (Customer customer : customers) {
            Row row = super.createRow(rowCount++);
            columnCount = startColumn;

            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, customer.getName(), style);
            createCell(row, columnCount++, customer.getPhone(), style);
            createCell(row, columnCount++, customer.getDateOfBirth().toString(), style);
            createCell(row, columnCount++, customer.getContactAddress(), style);
            createCell(row, columnCount++, customer.getOrders().size(), style);
            createCell(row, columnCount++, DecimalUtil.format(OrderUtil.calculateTotal(customer.getOrders())), style);
            createCell(row, columnCount, DecimalUtil.format(customer.getTotalDebt()), style);
        }

        for (int i = startColumn; i < columnCount + startColumn; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (int) (sheet.getColumnWidth(i) * 1.2)); // Adjust the padding factor as needed
        }
    }


}
