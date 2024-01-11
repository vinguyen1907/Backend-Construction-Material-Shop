package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.User;
import com.example.cmsbe.services.interfaces.IUserService;
import com.example.cmsbe.utils.UserUtil;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class ExcelGenerator extends Generator {
    private final IUserService userService;

    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    protected int titleRowNum = 0;
    protected int dateRowNum = 2;
    protected int idRowNum = 3;
    protected int nameRowNum = 4;
    protected int headerRowNum = 6;
    protected int startColumn = 1;

    public ExcelGenerator(IUserService userService) {
        this.userService = userService;
        workbook = new XSSFWorkbook();
    }

    protected void createNewSheet(String sheetName) {
        sheet = workbook.createSheet(sheetName);
    }

    protected XSSFRow createRow(int rowNum) {
        return sheet.createRow(rowNum);
    }

    protected abstract void writeTitleLine();
    protected abstract void writeHeaderLine();
    protected abstract void writeDataLines();

    public void writeInfoLines() {
        CellStyle infoLabelStyle = workbook.createCellStyle();
        XSSFFont sheetInfoFont = workbook.createFont();
        sheetInfoFont.setBold(true);
        sheetInfoFont.setFontHeight(14);
        infoLabelStyle.setAlignment(HorizontalAlignment.RIGHT);
        infoLabelStyle.setFont(sheetInfoFont);

        CellStyle infoDataStyle = workbook.createCellStyle();
        XSSFFont infoDataFont = workbook.createFont();
        infoDataFont.setBold(false);
        infoDataFont.setFontHeight(14);
        infoDataStyle.setFont(infoDataFont);

        Row dateRow = createRow(dateRowNum);
        createCell(dateRow, startColumn, "Date: ", infoLabelStyle);
        createCell(dateRow, startColumn + 1, LocalDateTime.now(), infoDataStyle);

        User user = (new UserUtil(userService)).getUserFromAuthentication();
        Row createdUserIdRow = createRow(idRowNum);
        createCell(createdUserIdRow, startColumn, "User ID: ", infoLabelStyle);
        createCell(createdUserIdRow, startColumn + 1, user.getId().toString(), infoDataStyle);

        Row createdUserNameRow = createRow(nameRowNum);
        createCell(createdUserNameRow, startColumn, "Name: ", infoLabelStyle);
        createCell(createdUserNameRow, startColumn + 1, user.getName(), infoDataStyle);
    }

    @Override
    public void export(HttpServletResponse response) throws IOException {
        writeTitleLine();
        writeInfoLines();
        writeHeaderLine();
        writeDataLines();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.close();
    }

    protected void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
        else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else if (value instanceof LocalDateTime) {
            DateTimeFormatter formatter =  DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
            cell.setCellValue(formatter.format((LocalDateTime) value));
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    protected CellStyle createTitleStyle() {
        CellStyle sheetStyle = workbook.createCellStyle();
        XSSFFont sheetNameFont = workbook.createFont();
        sheetNameFont.setBold(true);
        sheetNameFont.setFontHeight(18);
        sheetStyle.setFont(sheetNameFont);
        return sheetStyle;
    }

    protected CellStyle createHeaderStyle() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        font.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return style;
    }


    protected CellStyle createCellStyle() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        style.setWrapText(true);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }
}
