package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.Employee;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;

import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class EmployeeExcelGenerator extends ExcelGenerator {
    protected List<Employee> employees;

    public EmployeeExcelGenerator(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    protected void writeHeaderLine() {
        super.createNewSheet("Employees");

        Row row = super.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Employee code", style);
        createCell(row, 1, "Name", style);
        createCell(row, 2, "E-mail", style);
        createCell(row, 3, "Phone", style);
        createCell(row, 4, "Date of birth", style);
        createCell(row, 5, "Contact address", style);
        createCell(row, 6, "Salary", style);
        createCell(row, 7, "Start working date", style);
        createCell(row, 8, "Role", style);
    }

//    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
//        sheet.autoSizeColumn(columnCount);
//        Cell cell = row.createCell(columnCount);
//        if (value instanceof Integer) {
//            cell.setCellValue((Integer) value);
//        } else if (value instanceof Boolean) {
//            cell.setCellValue((Boolean) value);
//        }else {
//            cell.setCellValue((String) value);
//        }
//        cell.setCellStyle(style);
//    }

    @Override
    protected void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for (Employee employee : employees) {
            Row row = super.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, employee.getEmployeeCode(), style);
            sheet.autoSizeColumn(0);
            createCell(row, columnCount++, employee.getName(), style);
            sheet.autoSizeColumn(1);
            createCell(row, columnCount++, employee.getEmail(), style);
            sheet.autoSizeColumn(2);
            createCell(row, columnCount++, employee.getPhone(), style);
            sheet.autoSizeColumn(3);
            createCell(row, columnCount++, employee.getDateOfBirth().toString(), style);
            sheet.autoSizeColumn(4);
            createCell(row, columnCount++, employee.getContactAddress(), style);
            sheet.autoSizeColumn(5);
            createCell(row, columnCount++, employee.getSalary().toString(), style);
            sheet.autoSizeColumn(6);
            createCell(row, columnCount++, employee.getStartedWorkingDate().toString(), style);
            sheet.autoSizeColumn(7);
            createCell(row, columnCount, employee.getEmployeeType().toString(), style);
            sheet.autoSizeColumn(8);
        }
    }
}
