package ru.sashil.subscribe.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    private final By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
    private final By emailField = By.xpath("//*[@id='credential_0']");
    private final By passwordField = By.xpath("//*[@id='credential_1']");
    private final By loginButton = By.xpath("//input[@value='Войти' and @type='submit']");
    private final By errorMessage = By.xpath("//*[@id='auth_msg']/font");
    private final By successIndicator = By.xpath("//a[contains(@href, '/member/')] | //div[contains(text(), 'Мои подписки')]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickUserIcon() {
        wait.waitForClickable(userIcon);
        driver.findElement(userIcon).click();
    }

    public void enterEmail(String email) {
        wait.waitForVisible(emailField);
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLoginButton() {
        wait.waitForClickable(loginButton);
        driver.findElement(loginButton).click();
    }

    public String getEnteredEmail() {
        return driver.findElement(emailField).getAttribute("value");
    }

    public boolean isEmailFieldDisplayed() {
        return driver.findElements(emailField).size() > 0;
    }

    public boolean isPasswordFieldDisplayed() {
        return driver.findElements(passwordField).size() > 0;
    }

    public String getErrorMessageText() {
        if (driver.findElements(errorMessage).size() > 0) {
            return driver.findElement(errorMessage).getText();
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public boolean isLoginSuccessful() {
        return driver.findElements(successIndicator).size() > 0;
    }

    public void login(String email, String password) {
        clickUserIcon();
        enterEmail(email);
        enterPassword(password);
        clickLoginButton();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public org.openqa.selenium.By getErrorMessageLocator() {
        return errorMessage;
    }
}
