package com.tsoft.bot.frontend.application;

import com.tsoft.bot.frontend.base.BaseClass;
import com.tsoft.bot.frontend.helpers.Hook;
import com.tsoft.bot.frontend.objects.ExcelObjects;
import com.tsoft.bot.frontend.pages.*;
import org.openqa.selenium.WebDriver;

public class GenpolApplication {
    private BaseClass page;
    private final WebDriver browser = Hook.getDriver();
    private final ConsultPage consultPage = new ConsultPage(browser);
    private final smokePage smokePage = new smokePage(browser);

    public void iniciarGenpol(int dataRow, int userRow) throws Exception {
        ExcelObjects.loadData(dataRow, userRow);
        page = LoginPage.getInstance(browser);
        ((LoginPage) page).startApplication();
    }
    public void iniciarGenpol(int userRow) throws Exception {
        ExcelObjects.loadData(userRow);
        page = LoginPage.getInstance(browser);
        ((LoginPage) page).startApplication();
    }

    public void iniciarSesion() throws Exception {
        page = LoginPage.getInstance(browser);
        ((LoginPage) page).login();
        ((LoginPage) page).checkApplicationStartUp();
    }
    public void ingresaLosDatosGenerales() throws Exception {
        page = PolicyGeneratorPage.getInstance(browser);
        ((PolicyGeneratorPage) page).gettingEffectDateFromDB();
        ((PolicyGeneratorPage) page).fillGeneralData();
    }
    public void consultaLaPoliza() throws Exception {
        page = PolicyGeneratorPage.getInstance(browser);
        ((PolicyGeneratorPage) page).consultPolicy();
    }
    public void validarDatosDeLaPoliza() throws Exception {
        page = PolicyGeneratorPage.getInstance(browser);
        ((PolicyGeneratorPage) page).validatePDFGenerated();
    }
    public void renombrarArchivoWord() {
        page = PolicyGeneratorPage.getInstance(browser);
        ((PolicyGeneratorPage) page).renameWordFile();
    }
    public void renombrarArchivoWord(String test) {
        page = PolicyGeneratorPage.getInstance(browser);
        ((PolicyGeneratorPage) page).renameWordFile(test);
    }

    public void consultaDatos() throws Exception {
        consultPage.writeData();
    }

    public void urlLPG(int userRow) throws Exception {
        smokePage.startLPG(userRow);
    }
    public void urlLPV() throws Exception {
        smokePage.startLPV();
    }
    public void urlLPE() throws Exception {
        smokePage.startLPE();
    }
}