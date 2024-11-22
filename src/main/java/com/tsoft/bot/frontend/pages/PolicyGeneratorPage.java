package com.tsoft.bot.frontend.pages;

import com.tsoft.bot.both.utility.Excel;
import com.tsoft.bot.both.utility.PDF;
import com.tsoft.bot.both.utility.Regex;
import com.tsoft.bot.frontend.base.BaseClass;
import com.tsoft.bot.frontend.database.genpolDB;
import com.tsoft.bot.frontend.objects.ExcelObjects;
import com.tsoft.bot.frontend.objects.PolicyGeneratorObjects;
import org.openqa.selenium.WebDriver;

public class PolicyGeneratorPage extends BaseClass {
    protected static PolicyGeneratorPage instance = null;
    private PolicyGeneratorPage(WebDriver driver) {
        this.browser = driver;
    }
    public static PolicyGeneratorPage getInstance(WebDriver driver) {
        if (instance == null) {
            instance = new PolicyGeneratorPage(driver);
        } else {
            instance.browser = driver;
        }
        return instance;
    }
    private final PolicyGeneratorObjects objects = PolicyGeneratorObjects.getInstance();

    public void renameWordFile() {
        String test = ExcelObjects.data.get(ExcelObjects.GENPOL_TEST_CASE) +
                " - " +
                ExcelObjects.data.get(ExcelObjects.GENPOL_SCENERY) +
                " - " +
                ExcelObjects.data.get(ExcelObjects.GENPOL_TEST_DESCRIPTION) + "RENAMED BY EXCEL";
        generateWord.configureAlterName(test);
    }
    public void renameWordFile(String GherkinTest) {
        String test = GherkinTest + "RENAMED BY GHERKIN TEST";
        generateWord.configureAlterName(test);
    }
    public void gettingEffectDateFromDB() throws Exception {
        genpolDB db;
        try {
            message = "obtener datos de la base de datos - " + ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY).trim() + " - " + ExcelObjects.data.get(ExcelObjects.GENPOL_BRANCH).trim() + " | " + ExcelObjects.data.get(ExcelObjects.GENPOL_CORE).trim();
            db = genpolDB.getInstance();
            String numPolicy = db.getPolicy(ExcelObjects.data.get(ExcelObjects.GENPOL_CORE), ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY),ExcelObjects.data.get(ExcelObjects.GENPOL_TEST_CASE));
            Excel.save(ExcelObjects.GENPOL_POLICY, numPolicy);
            String effectDate = db.getEffectDate(ExcelObjects.data.get(ExcelObjects.GENPOL_CORE), ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY),ExcelObjects.data.get(ExcelObjects.GENPOL_POLICY));
            String data = "Se obtuvo correctamente los datos: " + effectDate + "\n\n" + db.getDBDetails(ExcelObjects.data.get(ExcelObjects.GENPOL_CORE), ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY));
            addReportData(data);
        } catch (Exception e) {
            db = genpolDB.getInstance();
            String data = "Error al obtener la fecha de efecto \n\n" + db.getDBDetails(ExcelObjects.data.get(ExcelObjects.GENPOL_CORE), ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY));
            addReportData(e,data);
        }
    }
    public void fillGeneralData() throws Exception {
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
            awaitElementToVisible(browser, objects.GENPOL_INP_END_CERTIFICATE);
            click(browser, objects.GENPOL_INP_END_CERTIFICATE);
            clear(browser, objects.GENPOL_INP_END_CERTIFICATE);
            typeText(browser, objects.GENPOL_INP_END_CERTIFICATE, ExcelObjects.data.get(ExcelObjects.GENPOL_CERTIFICATE));
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
        } catch (Exception e) {
            addReport(e);
        }
    }
    public void consultPolicy() throws Exception {
        try {
            message = "consultar la poliza - " + ExcelObjects.data.get(ExcelObjects.GENPOL_COMPANY).trim() + " - " + ExcelObjects.data.get(ExcelObjects.GENPOL_BRANCH).trim() + " | " + ExcelObjects.data.get(ExcelObjects.GENPOL_CORE).trim();
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
    public void validatePDFGenerated() throws Exception {
        try {
            message = "validar la aparicion del PDF";
            //System.out.println("el valor es:  "+browser.findElement(objects.GENPOL_FRAME_PDF).getAttribute("src"));
            //String data = PDF.readPDFFromURI(browser.findElement(objects.GENPOL_FRAME_PDF).getAttribute("src"));
            //String policy = Regex.getString(data,"Contrato[ ]*:[ ]*([\\d]*?(?=\\n|\\r\\n|\\r))",0, 1);
            //String procedure = Regex.getString(data,"Trámite[ ]*:[ ]*([\\d]*?(?=\\n|\\r\\n|\\r))",0,1);
            //String prima = Regex.getString(data,"Prima Total[ ]*([\\d\\D]+?(?=\\n|\\r\\n|\\r))",0,1);
            //String proforma = Regex.getString(data,"Número de Proforma[ ]*:[ ]*([\\d]*?(?= ))",0,1);
            //if (!policy.equals(ExcelObjects.data.get(ExcelObjects.GENPOL_POLICY).trim()))
            //    throw new Exception("no coincide el número de la póliza");
            //Excel.save(ExcelObjects.GENPOL_PROCEDURE, procedure);
            //Excel.save(ExcelObjects.GENPOL_PREMIUM_TOTAL, prima);
            //Excel.save(ExcelObjects.GENPOL_PROFORMA_NUMBER, proforma);
            //String content = "Validación de datos correcta. \n" +
            //        policy + " - " + "                        ".substring(policy.length()) +"✓\n" +
            //        procedure + " - " + "                        ".substring(procedure.length()) + "✓\n" +
            //        prima + " - " + "                        ".substring(prima.length()) + "✓\n" +
            //        proforma + " - " + "                        ".substring(proforma.length()) + "✓\n";
            addReportData(message);
        } catch (Exception e) {
            addReportData(e, "Error en la validación de datos.");
        }
    }
}
