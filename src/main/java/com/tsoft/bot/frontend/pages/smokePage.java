package com.tsoft.bot.frontend.pages;

import com.tsoft.bot.both.utility.Excel;
import com.tsoft.bot.both.utility.ExcelReader;
import com.tsoft.bot.both.utility.FileHelper;
import com.tsoft.bot.frontend.base.BaseClass;
import com.tsoft.bot.frontend.objects.ExcelObjects;
import com.tsoft.bot.frontend.objects.PolicyGeneratorObjects;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static com.tsoft.bot.frontend.objects.ExcelObjects.PAGE_USERS;
import static com.tsoft.bot.frontend.objects.ExcelObjects.selectSheet;

public class smokePage extends BaseClass {

    protected static smokePage instance = null;
    public smokePage(WebDriver driver) {
        this.browser = driver;
    }

    public static String ExcelPATH = "\\src\\main\\resources\\excel\\GENPOL.xlsx";
    public static int user_row = 1;
    public static String sheet;
    public static HashMap<String, String> data;


    private final PolicyGeneratorObjects objects = PolicyGeneratorObjects.getInstance();

    public void startLPG(int user) throws Exception {
        sheet = null;
        user_row = user;
        String path =  FileHelper.getProjectFolder() + ExcelPATH;
        selectSheet();
        ExcelObjects.userData = Excel.getValuesOfExcel(path, PAGE_USERS, user_row);
        try {
            message = "Se valida el correcto funcionamiento de la página LPG";
            this.browser.get("http://10.20.1.21/GeneradorPolizasLPG/policygenerator.aspx");


            robotCopyPaste(ExcelObjects.userData.get(ExcelObjects.GENPOL_USERNAME));
            robotTypeString("\t");
            sleep(1000);
            robotCopyPaste(ExcelObjects.userData.get(ExcelObjects.GENPOL_PASSWORD));
            robotTypeString("\n");
            awaitElementToVisible(browser, objects.GENPOL_SELECT_BRANCH,100);
            addReport();

        } catch (Exception we) {
            addReport(we);
        }
    }

    public void startLPV() throws Exception {
        try {
            message = "Se valida el correcto funcionamiento de la página LPV";
            this.browser.get("http://10.20.1.25/generadorpolizasvidaECM/policygenerator.aspx");
            awaitElementToVisible(browser, objects.GENPOL_SELECT_BRANCH,100);
            addReport();
        } catch (Exception we) {
            addReport(we);
        }
    }

    public void startLPE() throws Exception {
        try {
            message = "Se valida el correcto funcionamiento de la página LPE";
            this.browser.get("http://10.20.1.20/GeneradorPolizasLPE/policygenerator.aspx");
            awaitElementToVisible(browser, objects.GENPOL_SELECT_BRANCH,100);
            addReport();
        } catch (Exception we) {
            addReport(we);
        }
    }
}
