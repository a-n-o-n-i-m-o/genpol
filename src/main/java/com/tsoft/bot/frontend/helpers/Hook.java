package com.tsoft.bot.frontend.helpers;

import com.tsoft.bot.frontend.listener.Listener;
import com.tsoft.bot.both.utility.FileHelper;
import com.tsoft.bot.both.utility.GenerateWord;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.eo.Se;
import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.core.Serenity;
import net.thucydides.core.configuration.WebDriverConfiguration;
import net.thucydides.core.util.MockEnvironmentVariables;
import net.thucydides.core.webdriver.SerenityWebdriverManager;
import net.thucydides.core.webdriver.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.sikuli.script.Screen;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static com.tsoft.bot.frontend.base.BaseClass.stepFailNoShoot;

public class Hook extends Listener {

	private static final String GECKO_KEY 		= "webdriver.gecko.driver";
	private static final String GECKO_DRIVER 	= "/src/main/resources/driver/firefox/geckodriver.exe";
	private static final String CHROME_KEY 		= "webdriver.chrome.driver";
	private static final String CHROME_DRIVER 	= "/src/main/resources/driver/chrome131/chromedriver.exe";
	private static final String IE_KEY 			= "webdriver.ie.driver";
	private static final String IE_DRIVER 		= "/src/main/resources/driver/ie/3.5/IEDriverServer.exe";
	private static final long DELAY = 10;
	private static SerenityWebdriverManager SerenityDriver;
	private static WebDriver driver = null;
	private static Screen screen;

	private static GenerateWord generateWord;

	@Before
	public void scenario(Scenario scenario){
		if (!scenario.getName().contains("API") && !scenario.getName().contains("servicios"))
		{
			onTestStart(scenario.getName());
		}
	}

	@Before
	public static void setUpWeb(Scenario scenario) throws IOException {
		if (!scenario.getName().contains("API") && !scenario.getName().contains("servicios"))
		{
			generateWord = new GenerateWord();
			generateWord.startUpWord(scenario.getName());
			SerenityDriver = new SerenityWebdriverManager(new WebDriverFactory(),new WebDriverConfiguration(new MockEnvironmentVariables()));

			try {
				Logger.getLogger("[LOG] NAVEGADOR: resources");

				switch ("Chrome"){
					case "IE":
						System.setProperty(IE_KEY, FileHelper.getProjectFolder() + IE_DRIVER);
						InternetExplorerOptions options = new InternetExplorerOptions();
						options.introduceFlakinessByIgnoringSecurityDomains();
						options.setCapability("requireWindowFocus", true);
						options.setCapability("enablePersistentHover", false);
						options.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
						options.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
						options.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
						driver = new InternetExplorerDriver(options);
						SerenityDriver.setCurrentDriver(driver);
						break;
					case "Chrome":
						System.setProperty(CHROME_KEY, FileHelper.getProjectFolder() + CHROME_DRIVER);
						//WebDriverManager.chromedriver().setup();
						System.setProperty("webdriver.chrome.silentOutput", "true");
						ChromeOptions chromeOptions = new ChromeOptions();
						chromeOptions.addArguments(
								//"--headless",
								"--verbose",
								"--disable-web-security",
								"--ignore-certificate-errors",
								"--allow-running-insecure-content",
								"--allow-insecure-localhost",
								"--no-sandbox",
								"--disable-gpu",
								"enable-automation",
								"--disable-infobars",
								"--disable-dev-shm-usage",
								"--disable-browser-side-navigation"
						);
						driver = new ChromeDriver(chromeOptions);
						getDriver().manage().window().maximize();
						getDriver().manage().timeouts().implicitlyWait(DELAY, TimeUnit.SECONDS);
						SerenityDriver.setCurrentDriver(driver);
						break;

					case "Firefox":
						System.setProperty(GECKO_KEY, FileHelper.getProjectFolder() + GECKO_DRIVER);
						FirefoxOptions firefoxOptions = new FirefoxOptions();
						driver = new FirefoxDriver(firefoxOptions);
						getDriver().manage().window().maximize();
						getDriver().manage().timeouts().implicitlyWait(DELAY, TimeUnit.SECONDS);
						SerenityDriver.setCurrentDriver(driver);
						break;
					default:
						break;
				}

			}catch (Exception t){
				generateWord.sendText("Error : Navegador se cerro inesperandamente." + t.getMessage());
				stepFailNoShoot("Error : Navegador se cerro inesperandamente." + t.getMessage());
				throw t;
			}
		}
	}

	@After
	public void tearDown(Scenario scenario) throws IOException {
		if (!scenario.getName().contains("API") && !scenario.getName().contains("servicios"))
		{
			onFinish();
			//JOptionPane.showMessageDialog(null,"fin de flujo");
			generateWord.endToWord(scenario.getStatus().toString());

			driver.quit();
		}
	}

	public static WebDriver getDriver() { return driver; }


	public static Screen getScreen(){


		return (screen==null)?new Screen() : screen;
	}

}