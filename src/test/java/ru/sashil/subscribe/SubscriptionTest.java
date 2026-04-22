package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.sashil.subscribe.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionTest {

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
    }

    @Test
    @DisplayName("UC-04: Быстрая подписка - ожидается ошибка")
    void testQuickSubscribeError() {
        driver.get(BASE_URL + "member/quick?grp=digest.cookery");

        By errorMessage = By.xpath("//*[contains(text(), 'Не указан подписной адрес')]");
        boolean hasError = driver.findElements(errorMessage).size() > 0;

        assertTrue(hasError, "Ожидалась ошибка 'Не указан подписной адрес'");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
