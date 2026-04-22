package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateDigestUnauthorizedTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final String BASE_URL = "https://subscribe.ru/";

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("UC-09: Создание рассылки (неавторизованный пользователь - форма логина)")
    void testCreateDigestUnauthorized() throws InterruptedException {
        driver.get(BASE_URL);
        Thread.sleep(1000);

        By createDigestBtn = By.xpath("//*[@id='all']/section/div[1]/div/div/a[1]");
        wait.until(ExpectedConditions.elementToBeClickable(createDigestBtn)).click();

        // Ждём загрузки страницы
        wait.until(ExpectedConditions.urlContains("/member/list/new"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/list/new"),
                   "Должна открыться страница создания рассылки: " + currentUrl);

        // Проверяем, что форма логина отображается (мы не авторизованы)
        By emailField = By.xpath("//*[@id=\"credential_0\"]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));

        boolean hasLoginForm = driver.findElements(emailField).size() > 0;
        assertTrue(hasLoginForm, "Неавторизованный пользователь должен видеть форму логина");

        // Дополнительно проверяем поле пароля
        By passwordField = By.xpath("//*[@id=\"credential_1\"]");
        boolean hasPasswordField = driver.findElements(passwordField).size() > 0;
        assertTrue(hasPasswordField, "Поле пароля также должно быть видно");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
