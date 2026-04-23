package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.sashil.subscribe.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuickSubscribeTest {

    private WebDriver driver;
    private LoginPage loginPage;

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
    }

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Test
    @DisplayName("UC-04: Быстрая подписка - ожидается ошибка")
    void testQuickSubscribeError() throws InterruptedException {
        driver.get(BASE_URL + "member/quick?grp=digest.cookery");
        Thread.sleep(2000);

        String pageSource = driver.getPageSource();
        boolean hasError = pageSource.contains("Не указан подписной адрес");

        assertTrue(hasError, "На странице должна быть надпись 'Не указан подписной адрес'");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
