package com.tsoft.bot.frontend.pages;

import com.tsoft.bot.frontend.base.BaseClass;
import com.tsoft.bot.frontend.objects.ExcelObjects;
import com.tsoft.bot.frontend.objects.PolicyGeneratorObjects;
import org.openqa.selenium.WebDriver;

public class ConsultPage extends BaseClass {
    protected static ConsultPage instance = null;
    public ConsultPage(WebDriver driver) {
        this.browser = driver;
    }

    public static ConsultPage getInstance(WebDriver driver) {
        if (instance == null) {
            instance = new ConsultPage(driver);
        } else {
            instance.browser = driver;
        }
        return instance;
    }
    private final PolicyGeneratorObjects objects = PolicyGeneratorObjects.getInstance();

    public void writeData() throws Exception {
        try {
            message = "llenar los datos generales para la consulta";
            awaitElementToVisible(browser, objects.GENPOL_SELECT_BRANCH);
            click(browser, objects.GENPOL_SELECT_BRANCH);
            awaitElementToVisible(browser, objects.GENPOL_SELECT_BRANCH_OPTIONS);
            int size = browser.findElements(objects.GENPOL_SELECT_BRANCH_OPTIONS).size();
            int x = 0;
            String branch = ExcelObjects.data.get(ExcelObjects.GENPOL_BRANCH).trim().toLowerCase();
            boolean found = false;
            while (x < size) {
                if (browser.findElements(objects.GENPOL_SELECT_BRANCH_OPTIONS).get(x).getAttribute("textContent").toLowerCase().trim().equals(branch)) {
                    found = true;
                    break;
                }
                x += 1;
            }
            if (!found) throw new Exception("Ramo no encontrado");
            browser.findElements(objects.GENPOL_SELECT_BRANCH_OPTIONS).get(x).click();
            awaitElementToInvisible(browser, objects.GENPOL_PROGRESS);
            awaitElementToVisible(browser, objects.GENPOL_INP_POLICY);
            click(browser, objects.GENPOL_INP_POLICY);
            clear(browser, objects.GENPOL_INP_POLICY);
            typeText(browser, objects.GENPOL_INP_POLICY, ExcelObjects.data.get(ExcelObjects.GENPOL_POLICY));
            awaitElementToVisible(browser, objects.GENPOL_INP_START_CERTIFICATE);
            click(browser, objects.GENPOL_INP_START_CERTIFICATE);
            clear(browser, objects.GENPOL_INP_START_CERTIFICATE);
            typeText(browser, objects.GENPOL_INP_START_CERTIFICATE, ExcelObjects.data.get(ExcelObjects.GENPOL_CERTIFICATE));
            //awaitElementToVisible(browser, objects.GENPOL_INP_END_CERTIFICATE);
            //click(browser, objects.GENPOL_INP_END_CERTIFICATE);
            //clear(browser, objects.GENPOL_INP_END_CERTIFICATE);
            //typeText(browser, objects.GENPOL_INP_END_CERTIFICATE, ExcelObjects.data.get(ExcelObjects.GENPOL_CERTIFICATE));
            awaitElementToVisible(browser, objects.GENPOL_SELECT_TRANSACTION);
            click(browser, objects.GENPOL_SELECT_TRANSACTION);
            awaitElementToVisible(browser, objects.GENPOL_SELECT_TRANSACTION_OPTIONS);
            String transaction = ExcelObjects.data.get(ExcelObjects.GENPOL_TRANSACTION).toLowerCase().trim();
            size = browser.findElements(objects.GENPOL_SELECT_TRANSACTION_OPTIONS).size();
            x = 0;
            found = false;
            while (x < size) {
                if (browser.findElements(objects.GENPOL_SELECT_TRANSACTION_OPTIONS).get(x).getAttribute("textContent").toLowerCase().trim().equals(transaction)) {
                    found = true;
                    break;
                }
                x += 1;
            }
            if (!found) throw new Exception("Transacción no encontrada");
            browser.findElements(objects.GENPOL_SELECT_TRANSACTION_OPTIONS).get(x).click();
            awaitElementToInvisible(browser, objects.GENPOL_PROGRESS);
            awaitElementToVisible(browser, objects.GENPOL_INP_BEGIN_DATE);
            click(browser, objects.GENPOL_INP_BEGIN_DATE);
            clear(browser, objects.GENPOL_INP_BEGIN_DATE);
            typeText(browser, objects.GENPOL_INP_BEGIN_DATE, ExcelObjects.data.get(ExcelObjects.GENPOL_POLICY_START_DATE));
            addReport();

            awaitElementToVisible(browser, objects.GENPOL_BTN_CONSULT);
            click(browser, objects.GENPOL_BTN_CONSULT);
            awaitElementToInvisible(browser, objects.GENPOL_PROGRESS);
            awaitElementToVisible(browser, objects.GENPOL_FRAME_PDF);
            browser.switchTo().frame(browser.findElement(objects.GENPOL_FRAME_PDF));
            if (browser.getClass().toString().contains("chrome")) {
                awaitElementToVisible(browser, objects.GENPOL_EMBED);
                sleep(2400);
            } else if (browser.getClass().toString().contains("firefox")) {
                awaitElementToVisible(browser, objects.GENPOL_TEXT_LAYER);
            }
            browser.switchTo().parentFrame();
            addReport();

        } catch (Exception e) {
            addReport(e);
        }
    }
}
