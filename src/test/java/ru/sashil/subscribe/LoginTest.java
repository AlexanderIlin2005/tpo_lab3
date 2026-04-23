package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest extends BaseTestNoLogin {

    @Test
    @DisplayName("UC-01: Успешная авторизация")
    void testSuccessfulLogin() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);

        boolean isLoggedIn = driver.findElements(By.xpath("//a[contains(@href, '/member/')] | //div[contains(text(), 'Мои подписки')]")).size() > 0;
        assertTrue(isLoggedIn, "Пользователь не авторизован");
    }

    @Test
    @DisplayName("UC-01: Неверные данные - сообщение об ошибке (неправильная почта)")
    void testInvalidLoginWrongEmail() {
        driver.get(BASE_URL);

        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By emailField = By.xpath("//*[@id='credential_0']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys("wrong@email.com");

        By passwordField = By.xpath("//*[@id='credential_1']");
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys("887199");

        By loginBtn = By.xpath("//input[@value='Войти' and @type='submit']");
        driver.findElement(loginBtn).click();

        By errorMsg = By.xpath("//*[@id='auth_msg']/font | //em/font[contains(text(), 'не зарегистрирован')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));

        String errorText = driver.findElement(errorMsg).getText();
        assertTrue(errorText.contains("не зарегистрирован"), "Сообщение об ошибке не соответствует ожидаемому: " + errorText);

        assertTrue(driver.findElement(emailField).isDisplayed(), "Форма должна оставаться открытой");
        assertTrue(driver.findElement(passwordField).isDisplayed(), "Поле пароля должно быть видно");
    }

    @Test
    @DisplayName("UC-01: Неверные данные - сообщение об ошибке (неправильный пароль)")
    void testInvalidLoginWrongPassword() {
        driver.get(BASE_URL);

        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By emailField = By.xpath("//*[@id='credential_0']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(EMAIL);

        By passwordField = By.xpath("//*[@id='credential_1']");
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys("wrongpassword");

        By loginBtn = By.xpath("//input[@value='Войти' and @type='submit']");
        driver.findElement(loginBtn).click();

        By errorMsg = By.xpath("//*[@id='auth_msg']/font | //em/font[contains(text(), 'неверный пароль')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));

        String errorText = driver.findElement(errorMsg).getText();
        assertTrue(errorText.contains("неверный пароль"), "Сообщение об ошибке не соответствует ожидаемому: " + errorText);

        assertTrue(driver.findElement(emailField).isDisplayed(), "Форма должна оставаться открытой");

        String enteredEmail = driver.findElement(emailField).getAttribute("value");
        assertEquals(EMAIL, enteredEmail, "Email должен остаться в поле после ошибки");
    }
}
