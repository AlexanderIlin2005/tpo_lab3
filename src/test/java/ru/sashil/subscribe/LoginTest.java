package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sashil.subscribe.pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    private final String BASE_URL = "https://subscribe.ru/";
    private final String EMAIL = "hacks.scraper_1r@icloud.com";
    private final String PASSWORD = "887199";

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    @DisplayName("UC-01: Успешная авторизация")
    void testSuccessfulLogin() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);

        // Проверяем, что авторизация прошла успешно
        boolean isLoggedIn = driver.findElements(By.xpath("//a[contains(@href, '/member/')] | //div[contains(text(), 'Мои подписки')]")).size() > 0;
        assertTrue(isLoggedIn, "Пользователь не авторизован");
    }

    @Test
    @DisplayName("UC-01: Неверные данные - сообщение об ошибке (неправильная почта)")
    void testInvalidLoginWrongEmail() {
        driver.get(BASE_URL);

        // Кликаем по иконке человечка
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        // Ждём появления формы
        By emailField = By.xpath("//*[@id='credential_0']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        // Вводим НЕправильную почту и правильный пароль
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys("wrong@email.com");

        By passwordField = By.xpath("//*[@id='credential_1']");
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys("887199");

        // Нажимаем кнопку входа
        By loginBtn = By.xpath("//input[@value='Войти' and @type='submit']");
        driver.findElement(loginBtn).click();

        // Ждём появления сообщения об ошибке
        By errorMsg = By.xpath("//*[@id='auth_msg']/font | //em/font[contains(text(), 'не зарегистрирован')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));

        String errorText = driver.findElement(errorMsg).getText();
        assertTrue(errorText.contains("не зарегистрирован") || errorText.contains("не зарегистрирован"),
                   "Сообщение об ошибке не соответствует ожидаемому: " + errorText);

        // Проверяем, что форма осталась открытой (поля всё ещё видны)
        assertTrue(driver.findElement(emailField).isDisplayed(), "Форма должна оставаться открытой");
        assertTrue(driver.findElement(passwordField).isDisplayed(), "Поле пароля должно быть видно");
    }

    @Test
    @DisplayName("UC-01: Неверные данные - сообщение об ошибке (неправильный пароль)")
    void testInvalidLoginWrongPassword() {
        driver.get(BASE_URL);

        // Кликаем по иконке человечка
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        // Ждём появления формы
        By emailField = By.xpath("//*[@id='credential_0']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        // Вводим правильную почту, но НЕправильный пароль
        driver.findElement(emailField).clear();
        driver.findElement(emailField).sendKeys(EMAIL);

        By passwordField = By.xpath("//*[@id='credential_1']");
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys("wrongpassword");

        // Нажимаем кнопку входа
        By loginBtn = By.xpath("//input[@value='Войти' and @type='submit']");
        driver.findElement(loginBtn).click();

        // Ждём появления сообщения об ошибке
        By errorMsg = By.xpath("//*[@id='auth_msg']/font | //em/font[contains(text(), 'неверный пароль')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMsg));

        String errorText = driver.findElement(errorMsg).getText();
        assertTrue(errorText.contains("неверный пароль") || errorText.contains("неверный"),
                   "Сообщение об ошибке не соответствует ожидаемому: " + errorText);

        // Проверяем, что форма осталась открытой
        assertTrue(driver.findElement(emailField).isDisplayed(), "Форма должна оставаться открытой");

        // Проверяем, что введённые данные НЕ ОЧИСТИЛИСЬ (остался правильный email)
        String enteredEmail = driver.findElement(emailField).getAttribute("value");
        assertEquals(EMAIL, enteredEmail, "Email должен остаться в поле после ошибки");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
