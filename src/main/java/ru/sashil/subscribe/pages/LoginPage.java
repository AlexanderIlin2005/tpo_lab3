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
    private final By profileIndicator = By.xpath("//*[@id='all']/header/ul/li[1]/a"); // иконка человечка после входа

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String email, String password) {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Кликаем по иконке человечка
        wait.waitForClickable(userIcon);
        driver.findElement(userIcon).click();

        // Ждём появления формы
        wait.waitForVisible(emailField);

        // Вводим email
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(email);

        // Вводим пароль
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);

        // Нажимаем кнопку входа
        wait.waitForClickable(loginButton);
        driver.findElement(loginButton).click();

        // Ждём, пока форма логина исчезнет (станет невидимой)
        // Это основной признак успешного входа
        try {
            longWait.until(ExpectedConditions.invisibilityOfElementLocated(emailField));
        } catch (Exception e) {
            // Если форма не исчезла, проверяем наличие сообщения об ошибке
            if (driver.findElements(errorMessage).size() == 0) {
                // Если нет ни ошибки, просто ждём немного
                try { Thread.sleep(1000); } catch (InterruptedException ignored) {}
            }
        }
    }

    public String getErrorMessage() {
        if (driver.findElements(errorMessage).size() > 0) {
            return driver.findElement(errorMessage).getText();
        }
        return "";
    }

    public boolean isErrorMessageDisplayed() {
        return driver.findElements(errorMessage).size() > 0;
    }

    public boolean isFormDisplayed() {
        return driver.findElements(emailField).size() > 0 &&
               driver.findElements(passwordField).size() > 0;
    }

    public String getEnteredEmail() {
        return driver.findElement(emailField).getAttribute("value");
    }
}
