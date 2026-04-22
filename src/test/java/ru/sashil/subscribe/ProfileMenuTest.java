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
public class ProfileMenuTest {

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
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
    }

    @Test
    @DisplayName("Переход в Мой профиль через меню")
    void testGoToProfile() {
        // Кликаем по иконке человечка
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        // Кликаем по пункту "Мой профиль"
        By profileBtn = By.xpath("//*[@id='logged_list']/li[1]/a/span[2]");
        wait.until(ExpectedConditions.elementToBeClickable(profileBtn)).click();

        // Ждём загрузки страницы профиля
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Проверяем, что открылась страница профиля
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/") || currentUrl.contains("/profile"),
                   "Должна открыться страница профиля: " + currentUrl);
    }

    @Test
    @DisplayName("Переход в Мои подписки через меню")
    void testGoToSubscriptions() {
        // Кликаем по иконке человечка
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        // Кликаем по пункту "Мои подписки"
        By subscriptionsBtn = By.xpath("//*[@id='logged_list']/li[2]/a/span[2]");
        wait.until(ExpectedConditions.elementToBeClickable(subscriptionsBtn)).click();

        // Ждём загрузки
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Проверяем, что открылась страница с подписками
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/") || currentUrl.contains("/manage"),
                   "Должна открыться страница управления подписками: " + currentUrl);
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
