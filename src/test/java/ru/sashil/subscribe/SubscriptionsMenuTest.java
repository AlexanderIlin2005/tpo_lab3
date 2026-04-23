package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sashil.subscribe.pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionsMenuTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    private final String BASE_URL = "https://subscribe.ru/";
    private final String EMAIL = "hacks.scraper_1r@icloud.com";
    private final String PASSWORD = "887199";

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
    }

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userIcon));
    }

    @Test
    @DisplayName("UC-06: Переход в Мои подписки через меню")
    void testGoToSubscriptions() {
        By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By subscriptionsBtn = By.xpath("//*[@id=\"logged_list\"]/li[2]/a/span[2]");
        wait.until(ExpectedConditions.elementToBeClickable(subscriptionsBtn)).click();

        // Ждём изменения URL
        wait.until(ExpectedConditions.urlContains("/member/issue"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/issue"),
                   "Должна открыться страница /member/issue, а открылась: " + currentUrl);

        // Останавливаем бесконечную загрузку
        js.executeScript("window.stop();");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
