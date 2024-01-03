package com.example.cmsbe.services.generators;

import com.example.cmsbe.models.User;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public class ExcelGenerator extends Generator {
    protected XSSFWorkbook workbook;
    protected XSSFSheet sheet;
    protected List<User> listUsers;

    protected void writeHeaderLine() {}
    protected  void writeDataLines() {}
}
