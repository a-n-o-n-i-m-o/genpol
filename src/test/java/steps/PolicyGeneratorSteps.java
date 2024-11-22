package steps;

import com.tsoft.bot.frontend.application.GenpolApplication;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

public class PolicyGeneratorSteps {
    private final GenpolApplication application = new GenpolApplication();


    @Given("inicia la aplicación {int} {int}")
    public void iniciaLaAplicacion(int data,int user) throws Exception {
        application.iniciarGenpol(data,user);
    }

    @Then("valida, guarda las evidencias {string}")
    public void guardaLasEvidencias(String test) {
        application.renombrarArchivoWord(test);
    }

    @Then("valida, guarda las evidencias")
    public void validaGuardaLasEvidencias() throws Exception {
        application.validarDatosDeLaPoliza();
        application.renombrarArchivoWord();
    }

    @And("inicia sesión en la aplicación")
    public void iniciaSesionEnLaAplicacion() throws Exception {
        application.iniciarSesion();
    }


    @When("realiza la consulta de la póliza")
    public void realizaLaConsultaDeLaPoliza() throws Exception {
        //application.ingresaLosDatosGenerales();
        //application.consultaLaPoliza();
        application.consultaDatos();
    }

    @Given("que el usuario se logue {int}")
    public void queElUsuarioSeLogue(int user) throws Exception {
        application.urlLPG(user);
    }

    @When("pasamos portodos los campos de los aplicativos")
    public void pasamosPortodosLosCamposDeLosAplicativos() throws Exception {
        application.urlLPV();

    }

    @Then("valida los distintos modulos de consulta")
    public void validaLosDistintosModulosDeConsulta() throws Exception {
        application.urlLPE();
    }
}
