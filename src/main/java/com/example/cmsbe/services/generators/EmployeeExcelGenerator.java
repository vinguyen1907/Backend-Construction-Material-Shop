package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class EmployeeExcelGenerator extends ExcelGenerator {
    public EmployeeExcelGenerator(List<User> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    @Override
    protected void writeHeaderLine() {
        sheet = workbook.createSheet("Users");

        Row row = sheet.createRow(0);

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

        for (User user : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, user.getEmployeeCode(), style);
            sheet.autoSizeColumn(0);
            createCell(row, columnCount++, user.getName(), style);
            sheet.autoSizeColumn(1);
            createCell(row, columnCount++, user.getEmail(), style);
            createCell(row, columnCount++, user.getPhone(), style);
            sheet.autoSizeColumn(3);
            createCell(row, columnCount++, user.getDateOfBirth().toString(), style);
            sheet.autoSizeColumn(4);
            createCell(row, columnCount++, user.getContactAddress(), style);
            createCell(row, columnCount++, user.getSalary().toString(), style);
            sheet.autoSizeColumn(6);
            createCell(row, columnCount++, user.getStartedWorkingDate().toString(), style);
            sheet.autoSizeColumn(7);
            createCell(row, columnCount++, user.getEmployeeType().toString(), style);
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
