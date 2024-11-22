package com.tsoft.bot.frontend.base;

import com.tsoft.bot.frontend.config.PropertiesFile;
import com.tsoft.bot.frontend.exceptions.FrontEndException;
import com.tsoft.bot.both.utility.ExtentReportUtil;
import com.tsoft.bot.both.utility.GenerateWord;
import com.tsoft.bot.both.utility.Sleeper;
import com.tsoft.bot.frontend.helpers.Hook;
import net.serenitybdd.core.Serenity;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Logger;


public class BaseClass {

    protected static GenerateWord generateWord = new GenerateWord();
    protected WebDriver browser = Hook.getDriver();
    protected String message;
    protected PropertiesFile properties = PropertiesFile.getInstance();
    private Robot robot = null;

    /*
    public BaseClass(WebDriver driver){
        browser = driver;
    } */

    protected void click(WebDriver driver, By locator) throws IOException {
        try {
            driver.findElement(locator).click();
        }catch (RuntimeException we){
            errorNoElementFound(driver, locator);
            throw we;
        }
    }

    protected void clear(WebDriver driver, By locator) throws IOException {
        try {
            driver.findElement(locator).clear();
        }catch (RuntimeException we){
            errorNoElementFound(driver, locator);
            throw we;
        }
    }

    protected void typeText(WebDriver driver, By locator, String inputText) throws IOException {
        try {
            driver.findElement(locator).sendKeys(inputText);
        }catch (RuntimeException we){
            errorNoElementFound(driver, locator);
            throw we;
        }
    }

    protected Boolean isDisplayed(WebDriver driver, By locator){
        try {
            return driver.findElement(locator).isDisplayed();
        }catch (NoSuchElementException we){
            driver.close();
            return false;
        }
    }

    protected void selectByVisibleText(WebDriver driver, By locator, String text) throws IOException {
        try {
            Select typeSelect = new Select(driver.findElement(locator));
            typeSelect.selectByVisibleText(text);
        }catch (RuntimeException we){
            errorNoElementFound(driver, locator);
            throw we;
        }
    }

    public static Exception handleError(WebDriver driver, String codigo, String msg) throws Throwable {
        stepWarning(driver, msg);
        return new FrontEndException(StringUtils.trimToEmpty(codigo), msg);
    }

    protected static void sleep(int milisegundos) {
        Sleeper.sleep(milisegundos);
    }

    protected static void stepPass(WebDriver driver, String descripcion) {
        try {
            ExtentReportUtil.INSTANCE.stepPass(driver, descripcion);
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-StepPass: " + t);
            throw t;
        } catch (Exception e) {
            Logger.getLogger("[LOG]-StepPass: " + e);
        }
    }

    private static void stepWarning(WebDriver driver, String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepWarning(driver, descripcion);
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-StepWarning: " + t);
            throw t;
        }
    }

    protected void stepFail(WebDriver driver, String descripcion) throws Exception {
        try {
            ExtentReportUtil.INSTANCE.stepFail(driver, descripcion);
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-StepFail: " + t);
            throw t;
        }
    }

    public static void stepFailNoShoot(String descripcion) {
        try {
            ExtentReportUtil.INSTANCE.stepFailNoShoot(descripcion);
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-StepFailNoShoot: " + t);
            throw t;
        }
    }

    public static void scroll(WebDriver driver, int x, int y) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollBy(" + x + "," + y + ")", "");
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-Scroll: " + t);
            throw t;
        }
    }
    public static void scrollingIn(WebDriver driver, By selector, int x, int y) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].scrollBy(" + x + "," + y + ")", driver.findElement(selector));
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-Scroll-Element: " + t);
            throw t;
        }
    }

    public static void zoom(WebDriver driver, int size) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom = '" + size + "%'");
        } catch (RuntimeException t) {
            Logger.getLogger("[LOG]-zoom: " + t);
            throw t;
        }
    }

    protected void errorNoElementFound(WebDriver driver, By locator) throws IOException {
        Serenity.recordReportData().withTitle("Error").andContents("- No se encontró el elemento : " + locator);
        generateWord.sendText("Error : No se encontró el elemento : " + locator);
        generateWord.addImageToWord(driver);
    }
    /* has been adding more methods*/
    protected void awaitElementToVisible(WebDriver driver, By locator) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }
    protected void awaitElementToVisible(WebDriver driver, By locator, int seconds) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
    }
    protected void awaitElementToClick(WebDriver driver, By locator) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
    }
    protected void awaitElementToClick(WebDriver driver, By locator, int seconds) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(locator)));
    }
    protected void awaitElementToInvisible(WebDriver driver, By locator) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, 40);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
    }
    protected void awaitElementToInvisible(WebDriver driver, By locator, int seconds) throws IOException {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(locator)));
    }
    private void createRobot() throws AWTException {
        if (robot == null) {
            robot = new Robot();
        }
    }
    protected void robotTypeString(String text) throws Exception {
        createRobot();
        int x = 0,length = text.length();
        while (x < length) {
            robot.keyPress(text.charAt(x));
            robot.keyRelease(text.charAt(x));
            x += 1;
        }
    }
    protected void robotCopyPaste(String text) throws Exception {
        createRobot();
        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
        robot.keyPress(17);
        sleep(100);
        robot.keyPress(86);
        sleep(100);
        robot.keyRelease(86);
        sleep(10);
        robot.keyRelease(17);
        sleep(10);
        stringSelection = new StringSelection("");
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);
    }
    protected void addReport() throws Exception {
        stepPass(browser,message);
        generateWord.sendText(message);
        generateWord.addImageToWord(browser);
        Serenity.takeScreenshot();
    }
    protected void addReportData(String content) {
        stepPass(browser,message);
        generateWord.sendText(message);
        generateWord.sendText(content);
        Serenity.recordReportData().withTitle(message).andContents(content);
    }
    protected void addReport(Exception we) throws Exception {
        message = "Falla al: " + message;
        stepFail(browser,message);
        generateWord.sendText(message);
        generateWord.addImageToWord(browser);
        Serenity.takeScreenshot();
        Serenity.recordReportData().withTitle("Error").andContents(message);
        // throw new Exception(message);
        throw we;
    }
    protected void addReportData(Exception we, String content) throws Exception {
        message = "Falla al: " + message;
        stepFail(browser,message);
        generateWord.sendText(message);
        generateWord.sendText(content);
        Serenity.recordReportData().withTitle(message).andContents(content);
        // throw new Exception(message);
        throw we;
    }
}