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
public class SubscriptionsListTest {

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

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Test
    @DisplayName("UC-06: Просмотр списка активных подписок")
    void testViewSubscriptions() {
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By subscriptionsBtn = By.xpath("//*[@id='logged_list']/li[2]/a");
        wait.until(ExpectedConditions.elementToBeClickable(subscriptionsBtn)).click();

        try { Thread.sleep(2000); } catch (InterruptedException e) {}

        By subscriptionsList = By.xpath("//table | //div[contains(text(), 'подписано')]");
        boolean hasList = driver.findElements(subscriptionsList).size() > 0;

        assertTrue(hasList, "Список подписок должен отображаться");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
