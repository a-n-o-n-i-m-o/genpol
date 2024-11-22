package com.tsoft.bot.frontend.objects;

import com.tsoft.bot.both.utility.Excel;
import com.tsoft.bot.both.utility.FileHelper;

import java.util.HashMap;

public class ExcelObjects {
    public static String ExcelPATH = "\\src\\main\\resources\\excel\\GENPOL.xlsx";
    public static int data_row;
    public static int user_row = 1;
    public static String sheet;
    public static HashMap<String, String> data;
    public static HashMap<String, String> userData;

    public static void loadData(int row, int user) throws Exception {
        sheet = null;
        data_row = row;
        user_row = user;
        String path =  FileHelper.getProjectFolder() + ExcelPATH;
        selectSheet();
        ExcelObjects.data = Excel.getValuesOfExcel(path, sheet, data_row);
        ExcelObjects.userData = Excel.getValuesOfExcel(path, PAGE_USERS, user_row);
    }
    public static void loadData() throws Exception {
        String path =  FileHelper.getProjectFolder() + ExcelPATH;
        ExcelObjects.userData = Excel.getValuesOfExcel(path, PAGE_USERS, user_row);
    }
    public static void loadData(int user) throws Exception {
        String path =  FileHelper.getProjectFolder() + ExcelPATH;
        user_row = user;
        ExcelObjects.userData = Excel.getValuesOfExcel(path, PAGE_USERS, user_row);
    }

    public static void selectSheet() {
        sheet = PAGE_POLICY_GENERATOR;
    }

    public static final String PAGE_USERS = "usuarios";
    public static final String PAGE_POLICY_GENERATOR = "GENPOL";
    public static final String GENPOL_SCENERY = "escenario";
    public static final String GENPOL_TEST_CASE = "caso de prueba";
    public static final String GENPOL_TEST_DESCRIPTION = "descripción";
    public static final String GENPOL_COMPANY = "compañía";
    public static final String GENPOL_CORE = "core";
    public static final String GENPOL_BRANCH = "ramo";
    public static final String GENPOL_POLICY = "póliza";
    public static final String GENPOL_PROCEDURE = "trámite";
    public static final String GENPOL_PREMIUM_TOTAL = "prima total";
    public static final String GENPOL_PROFORMA_NUMBER = "número de proforma";
    public static final String GENPOL_CERTIFICATE = "certificado";
    public static final String GENPOL_TRANSACTION = "transacción";
    public static final String GENPOL_POLICY_START_DATE = "fecha de inicio de la póliza";
    public static final String GENPOL_USERNAME = "usuario";
    public static final String GENPOL_PASSWORD = "contraseña";

}
