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

    public void iniciarSesion() throws Exception {
    // Crear una espera explícita de 10 segundos
    WebDriverWait wait = new WebDriverWait(browser, Duration.ofSeconds(10));

    // Esperar hasta que el elemento de la página de inicio de sesión esté disponible
    WebElement loginButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginButtonId"))); // Reemplazar con el ID correcto

    // Iniciar la sesión
    page = LoginPage.getInstance(browser);
    ((LoginPage) page).login();

    // Esperar hasta que la página se haya cargado completamente después del login
    WebElement appStartElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("applicationStartId"))); // Reemplazar con el ID correcto
    ((LoginPage) page).checkApplicationStartUp();
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
