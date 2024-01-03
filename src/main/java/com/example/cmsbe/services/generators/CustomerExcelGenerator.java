package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.Customer;
import com.example.cmsbe.utils.OrderUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class CustomerExcelGenerator extends ExcelGenerator {
    private final List<Customer> customers;

    public CustomerExcelGenerator(List<Customer> customers) {
        super();
        this.customers = customers;
    }

    @Override
    protected void writeHeaderLine() {
        super.createNewSheet("Customers");

        Row row = super.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Name", style);
        createCell(row, 1, "Phone", style);
        createCell(row, 2, "Date of birth", style);
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "Contact address", style);
        createCell(row, 5, "Orders", style);
        createCell(row, 6, "Orders Value", style);
    }

    @Override
    protected void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Customer customer : customers) {
            Row row = super.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, customer.getName(), style);
            sheet.autoSizeColumn(0);
            createCell(row, columnCount++, customer.getPhone(), style);
            sheet.autoSizeColumn(1);
            createCell(row, columnCount++, customer.getDateOfBirth().toString(), style);
            sheet.autoSizeColumn(2);
            createCell(row, columnCount++, customer.getContactAddress(), style);
            sheet.autoSizeColumn(3);
            createCell(row, columnCount++, customer.getDateOfBirth().toString(), style);
            sheet.autoSizeColumn(4);
            createCell(row, columnCount++, String.valueOf((long) customer.getOrders().size()), style);
            sheet.autoSizeColumn(5);
            createCell(row, columnCount, String.valueOf(OrderUtil.calculateTotal(customer.getOrders())), style);
            sheet.autoSizeColumn(6);
        }
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }
}
