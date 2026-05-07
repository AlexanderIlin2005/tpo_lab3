package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.sashil.subscribe.utils.EnvLoader;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest extends BaseTestNoLogin {

    private final String WRONG_PASSWORD = "wrongpassword";

    @Test
    @DisplayName("UC-01: Успешная авторизация")
    void testSuccessfulLogin() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        assertTrue(loginPage.isLoginSuccessful(), "Пользователь не авторизован");
    }

    @Test
    @DisplayName("UC-01: Неверные данные - сообщение об ошибке (неправильная почта)")
    void testInvalidLoginWrongEmail() {
        driver.get(BASE_URL);
        loginPage.clickUserIcon();
        loginPage.enterEmail("wrong@email.com");
        loginPage.enterPassword(PASSWORD);
        loginPage.clickLoginButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath("//*[@id='auth_msg']/font")));
        
        String errorText = loginPage.getErrorMessageText();
        assertTrue(errorText.contains("не зарегистрирован"), "Сообщение об ошибке не соответствует ожидаемому: " + errorText);
        assertTrue(loginPage.isEmailFieldDisplayed(), "Форма должна оставаться открытой");
        assertTrue(loginPage.isPasswordFieldDisplayed(), "Поле пароля должно быть видно");
    }

    @Test
    @DisplayName("UC-01: Неверные данные - сообщение об ошибке (неправильный пароль)")
    void testInvalidLoginWrongPassword() {
        driver.get(BASE_URL);
        loginPage.clickUserIcon();
        loginPage.enterEmail(EMAIL);
        loginPage.enterPassword(WRONG_PASSWORD);
        loginPage.clickLoginButton();

        wait.until(ExpectedConditions.visibilityOfElementLocated(org.openqa.selenium.By.xpath("//*[@id='auth_msg']/font")));
        
        String errorText = loginPage.getErrorMessageText();
        assertTrue(errorText.contains("неверный пароль"), "Сообщение об ошибке не соответствует ожидаемому: " + errorText);
        assertTrue(loginPage.isEmailFieldDisplayed(), "Форма должна оставаться открытой");

        String enteredEmail = loginPage.getEnteredEmail();
        assertEquals(EMAIL, enteredEmail, "Email должен остаться в поле после ошибки");
    }
}
