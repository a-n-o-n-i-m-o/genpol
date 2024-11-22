package com.tsoft.bot.both.utility;

import com.tsoft.bot.frontend.objects.ExcelObjects;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class Excel {
    public static  HashMap<String, String> getValuesOfExcel(String path, String sheet, int row) throws Exception {
        HashMap<String, String> Data = new HashMap<>();

        FileInputStream fis;
        XSSFWorkbook workbook = null;

        try {
            File file =  new File(path);
            if (!file.exists()) throw new Exception("el archivo " + file.getAbsolutePath() + " no existe");
            fis = new FileInputStream(file);
            workbook =  new XSSFWorkbook(fis);
            XSSFSheet sheetWork = workbook.getSheet(sheet);

            Row headerRow = sheetWork.getRow(0);
            int cells = headerRow.getPhysicalNumberOfCells();

            Row currentRow = sheetWork.getRow(row);

            for (int cell = 0; cell < cells; cell++) {
                Cell currentCell = currentRow.getCell(cell);
                if (currentCell != null) {
                    switch (currentCell.getCellTypeEnum().toString()) {
                        case "STRING":
                            Data.put(StringUtils.trimToEmpty(headerRow.getCell(cell).getStringCellValue()),StringUtils.trimToEmpty(currentCell.getStringCellValue()));
                            break;
                        case "NUMERIC":
                            double number = currentCell.getNumericCellValue();
                            String inputString;
                            if (number % 1 != 0)
                                inputString = String.valueOf(number);
                            else
                                inputString = String.valueOf(((int) number));
                            Data.put(StringUtils.trimToEmpty(headerRow.getCell(cell).getStringCellValue()),inputString);
                            break;
                        case "DATE":
                            Data.put(StringUtils.trimToEmpty(headerRow.getCell(cell).getStringCellValue()),StringUtils.trimToEmpty(String.valueOf(currentCell.getDateCellValue())));
                            break;
                        case "BOOLEAN":
                            Data.put(StringUtils.trimToEmpty(headerRow.getCell(cell).getStringCellValue()),StringUtils.trimToEmpty(String.valueOf(currentCell.getBooleanCellValue())));
                            break;
                        default:
                            Data.put(StringUtils.trimToEmpty(headerRow.getCell(cell).getStringCellValue()),StringUtils.EMPTY);
                            break;
                    }
                } else
                    Data.put(StringUtils.trimToEmpty(headerRow.getCell(cell).getStringCellValue()), StringUtils.EMPTY);
            }
        } finally {
            if (workbook != null)
                workbook.close();
        }
        return Data;
    }
    public static void save(String sheet, String column, int row, String data) throws Exception {
        File file =  new File(FileHelper.getProjectFolder() + ExcelObjects.ExcelPATH);
        if (!file.exists()) throw new Exception("el archivo " + file.getAbsolutePath() + " no existe");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook =  new XSSFWorkbook(fis);
        XSSFSheet sheetWork = workbook.getSheet(sheet);
        int location = 0;
        boolean found = false;
        while (location < sheetWork.getRow(0).getLastCellNum()) {
            if (sheetWork.getRow(0).getCell(location).getStringCellValue().equals(column))  {
                found = true;
                break;
            }
            location += 1;
        }
        if (found){
            try {
                sheetWork.getRow(row).getCell(location).setCellValue(data);
            } catch (Exception e) {
                sheetWork.getRow(row).createCell(location).setCellValue(data);
            }
            fis.close();
            OutputStream result = new FileOutputStream(file);
            workbook.write(result);

            result.flush();
            result.close();
        } else {
            workbook.close();
            throw new Exception("no existe la columna");
        }
        workbook.close();
        ExcelObjects.data.replace(column,data);
    }
    public static void save(String column, String data) throws Exception {
        int row = ExcelObjects.data_row;
        File file =  new File(FileHelper.getProjectFolder() + ExcelObjects.ExcelPATH);
        if (!file.exists()) throw new Exception("el archivo " + file.getAbsolutePath() + " no existe");
        FileInputStream fis = new FileInputStream(file);
        XSSFWorkbook workbook =  new XSSFWorkbook(fis);
        XSSFSheet sheetWork = workbook.getSheet(ExcelObjects.sheet);
        int location = 0;
        boolean found = false;
        while (location < sheetWork.getRow(0).getLastCellNum()) {
            if (sheetWork.getRow(0).getCell(location).getStringCellValue().equals(column))  {
                found = true;
                break;
            }
            location += 1;
        }
        if (found){
            try {
                sheetWork.getRow(row).getCell(location).setCellValue(data);
            } catch (Exception e) {
                sheetWork.getRow(row).createCell(location).setCellValue(data);
            }
            fis.close();
            OutputStream result = new FileOutputStream(file);
            workbook.write(result);

            result.flush();
            result.close();
        } else {
            workbook.close();
            throw new Exception("no existe la columna");
        }
        workbook.close();
        ExcelObjects.data.replace(column,data);
    }
}
