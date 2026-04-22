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
    }

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userIcon));
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }

    @Test
    @DisplayName("Переход в Мои подписки через меню")
    void testGoToSubscriptions() {
        By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        try { Thread.sleep(500); } catch (InterruptedException e) {}

        By subscriptionsBtn = By.xpath("//*[@id=\"logged_list\"]/li[2]/a/span[2]");
        wait.until(ExpectedConditions.elementToBeClickable(subscriptionsBtn)).click();

        // Ждём изменения URL
        long startTime = System.currentTimeMillis();
        while (!driver.getCurrentUrl().contains("/member/issue") &&
               (System.currentTimeMillis() - startTime) < 10000) {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/issue"),
                   "Должна открыться страница /member/issue, а открылась: " + currentUrl);

        // Останавливаем бесконечную загрузку
        try {
            ((JavascriptExecutor) driver).executeScript("window.stop();");
        } catch (Exception e) {}
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
