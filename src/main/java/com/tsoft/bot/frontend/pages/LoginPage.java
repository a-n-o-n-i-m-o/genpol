package com.tsoft.bot.frontend.pages;

import com.tsoft.bot.both.utility.Excel;
import com.tsoft.bot.frontend.base.BaseClass;
import com.tsoft.bot.frontend.objects.ExcelObjects;
import com.tsoft.bot.frontend.objects.PolicyGeneratorObjects;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BaseClass {
    protected static LoginPage instance = null;
    private LoginPage(WebDriver driver) {
        this.browser = driver;
    }
    public static LoginPage getInstance(WebDriver driver) {
        if (instance == null) {
            instance = new LoginPage(driver);
        } else {
            instance.browser = driver;
        }
        return instance;
    }
    private final PolicyGeneratorObjects objects = PolicyGeneratorObjects.getInstance();
    public void startApplication() throws Exception {
        try {
            message = "iniciando la aplicación";
            String url = properties.getApplicationUrl(ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY));
            browser.get(url);
        } catch (Exception we) {
            addReport(we);
        }
    }
    public void login() throws Exception {
        if (ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY).equals("LPG")) {
            try {
                message = "iniciar sesión en la aplicación";
                robotCopyPaste(ExcelObjects.userData.get(ExcelObjects.GENPOL_USERNAME));
                robotTypeString("\t");
                sleep(1000);
                robotCopyPaste(ExcelObjects.userData.get(ExcelObjects.GENPOL_PASSWORD));
                robotTypeString("\n");
            } catch (Exception we) {
                addReport(we);
            }
        }
    }
    public void checkApplicationStartUp() throws Exception {
        try {
            message = "inicar la aplicación";
            awaitElementToVisible(browser, objects.GENPOL_SELECT_BRANCH,100);
            addReport();
        } catch (Exception we) {
            addReport(we);
        }
    }
}
