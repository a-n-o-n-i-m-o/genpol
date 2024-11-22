package com.tsoft.bot.frontend.objects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PolicyGeneratorObjects {
    protected static PolicyGeneratorObjects instance = null;
    private PolicyGeneratorObjects() { }
    public static PolicyGeneratorObjects getInstance() {
        if (instance == null) {
            instance = new PolicyGeneratorObjects();
        }
        return instance;
    }
    public final By GENPOL_PROGRESS = By.cssSelector("[id$=\"progress\"]");
    public final By GENPOL_BTN_CONSULT = By.cssSelector("[id$=\"btnConsultar\"]");
    public final By GENPOL_SELECT_BRANCH = By.cssSelector("[id$=\"ddlRamo\"]");
    public final By GENPOL_SELECT_BRANCH_OPTIONS = By.cssSelector("[id$=\"ddlRamo\"] option:not([value=\"0\"])");
    public final By GENPOL_INP_POLICY = By.cssSelector("[id$=\"txtPoliza\"]");
    public final By GENPOL_INP_START_CERTIFICATE = By.cssSelector("[id$=\"txtCertificadoInicial\"]");
    public final By GENPOL_INP_END_CERTIFICATE = By.cssSelector("[id$=\"txtCertificadoFinal\"]");
    public final By GENPOL_SELECT_TRANSACTION = By.cssSelector("[id$=\"ddlTipoTransaccion\"]");
    public final By GENPOL_SELECT_TRANSACTION_OPTIONS = By.cssSelector("[id$=\"ddlTipoTransaccion\"] option:not([value=\"0\"])");
    public final By GENPOL_INP_BEGIN_DATE = By.cssSelector("[id$=\"txtFechaInicio\"]");
    public final By GENPOL_FRAME_PDF = By.cssSelector("[id$=\"IframePDF\"]");
    public final By GENPOL_DOWNLOAD_PDF = By.cssSelector("[id=\"download\"]");
    public final By GENPOL_TEXT_LAYER = By.cssSelector("#viewer .textLayer");
    public final By GENPOL_EMBED = By.cssSelector("embed");

}
