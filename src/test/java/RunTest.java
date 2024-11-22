import cucumber.api.testng.AbstractTestNGCucumberTests;
import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.thucydides.core.annotations.Screenshots;
import org.junit.runner.RunWith;
import org.testng.annotations.Test;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber"},
        tags = "@GENPOL",
        features = {
                "src/test/resources/features/genpol",
        },
        glue={
                "steps",
                "com.tsoft.bot.frontend.helpers",
                "com.tsoft.bot.frontend.steps"
        }
)
@Test
// @Screenshots(forEachAction=true)
public class RunTest extends AbstractTestNGCucumberTests {}
