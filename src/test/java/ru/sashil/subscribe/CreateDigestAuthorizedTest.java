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
public class CreateDigestAuthorizedTest {

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
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test
    @DisplayName("UC-09: Создание рассылки (авторизованный пользователь)")
    void testCreateDigestAuthorized() throws InterruptedException {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        Thread.sleep(2000);

        By createDigestBtn = By.xpath("//*[@id='all']/section/div[1]/div/div/a[1]");
        wait.until(ExpectedConditions.elementToBeClickable(createDigestBtn)).click();

        wait.until(ExpectedConditions.urlContains("/member/list/new"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/list/new"),
                   "Должна открыться страница создания рассылки: " + currentUrl);

        // Проверяем, что форма логина НЕ отображается (мы уже авторизованы)
        By emailField = By.xpath("//*[@id=\"credential_0\"]");
        boolean hasLoginForm = driver.findElements(emailField).size() > 0;
        assertFalse(hasLoginForm, "Авторизованный пользователь не должен видеть форму логина");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
