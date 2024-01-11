package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.User;
import com.example.cmsbe.services.interfaces.IUserService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;

public class EmployeeExcelGenerator extends ExcelGenerator {
    protected List<User> listUsers;

    public EmployeeExcelGenerator(IUserService userService, List<User> listUsers) {
        super(userService);
        this.listUsers = listUsers;
    }

    @Override
    protected void writeTitleLine() {
        createNewSheet("Employees");

        CellStyle titleStyle = createTitleStyle();

        Row sheetTitleRow = createRow(titleRowNum);
        createCell(sheetTitleRow, startColumn, "CMS EMPLOYEE REPORT", titleStyle);
        var mergedRegionStart = startColumn;
        var mergedRegionEnd = 10;
        sheet.addMergedRegion(new CellRangeAddress(sheetTitleRow.getRowNum(), sheetTitleRow.getRowNum(), mergedRegionStart, mergedRegionEnd));
    }

    @Override
    protected void writeHeaderLine() {
        Row row = super.createRow(headerRowNum);
        row.setHeight((short) (row.getHeight() * 1.5));

        CellStyle style = createHeaderStyle();
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        createCell(row, startColumn, "No", style);
        createCell(row, startColumn + 1, "Employee code", style);
        createCell(row, startColumn + 2, "Name", style);
        createCell(row, startColumn + 3, "E-mail", style);
        createCell(row, startColumn + 4, "Phone", style);
        createCell(row, startColumn + 5, "Date of birth", style);
        createCell(row, startColumn + 6, "Contact address", style);
        createCell(row, startColumn + 7, "Salary", style);
        createCell(row, startColumn + 8, "Start working date", style);
        createCell(row, startColumn + 9, "Role", style);
    }

    @Override
    protected void writeDataLines() {
        int rowCount = headerRowNum + 1;

        CellStyle style = createCellStyle();

        int index = 0;
        int columnCount = startColumn;
        for (User user : listUsers) {
            Row row = super.createRow(rowCount++);
            columnCount = startColumn;

            createCell(row, columnCount++, index++, style);
            createCell(row, columnCount++, user.getEmployeeCode(), style);
            createCell(row, columnCount++, user.getName(), style);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            createCell(row, columnCount++, user.getDateOfBirth().toString(), style);
            createCell(row, columnCount++, user.getContactAddress(), style);
            createCell(row, columnCount++, user.getSalary().toString(), style);
            createCell(row, columnCount++, user.getStartedWorkingDate().toString(), style);
            createCell(row, columnCount, user.getEmployeeType().toString(), style);
        }

        for (int i = startColumn; i < columnCount + startColumn; i++) {
            sheet.autoSizeColumn(i);
            sheet.setColumnWidth(i, (int) (sheet.getColumnWidth(i) * 1.2)); // Adjust the padding factor as needed
        }
    }
}
