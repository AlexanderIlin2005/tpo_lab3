package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sashil.subscribe.pages.LoginPage;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MainPageSubscribeTest {

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
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @Test
    @DisplayName("UC-05: Подписка через главную страницу")
    void testSubscribeFromMainPage() throws InterruptedException {
        // ШАГ 1: Проверяем, что нет подписок
        driver.get(BASE_URL + "member/issue");
        Thread.sleep(5000);
        js.executeScript("window.stop();");
        Thread.sleep(500);

        String pageSourceBefore = driver.getPageSource();
        boolean hasNoSubscription = pageSourceBefore.contains("Не подписана") || pageSourceBefore.contains("не подписана");
        assertTrue(hasNoSubscription, "До подписки должна быть надпись об отсутствии подписок");

        // ШАГ 2: На главной странице подписываемся на первую рассылку
        driver.get(BASE_URL);
        Thread.sleep(3000);

        By subscribeBtn = By.xpath("//a[contains(@class, 'subscriberu_subscribe') and contains(@class, 'subscriberu_notsubscribed')]");
        WebElement firstSubscribe = driver.findElements(subscribeBtn).get(0);
        js.executeScript("arguments[0].click();", firstSubscribe);
        Thread.sleep(2000);

        // ШАГ 3: Проверяем, что подписка появилась
        driver.get(BASE_URL + "member/issue");
        Thread.sleep(5000);
        js.executeScript("window.stop();");
        Thread.sleep(500);

        String pageSourceAfter = driver.getPageSource();
        boolean hasSubscription = pageSourceAfter.contains("подписчик") || pageSourceAfter.contains("Отписаться");
        assertTrue(hasSubscription, "Подписка должна появиться в списке");

        // ШАГ 4: Отписываемся
        By unsubscribeBtn = By.xpath("//*[@id=\"all\"]/section/div[2]/div/div/div[1]/div[4]/div/span/a");
        if (driver.findElements(unsubscribeBtn).size() > 0) {
            js.executeScript("arguments[0].click();", driver.findElement(unsubscribeBtn));
            Thread.sleep(2000);
        }

        // ШАГ 5: Проверяем, что подписок снова нет
        driver.navigate().refresh();
        Thread.sleep(5000);
        js.executeScript("window.stop();");
        Thread.sleep(500);

        String pageSourceFinal = driver.getPageSource();
        boolean hasNoSubscriptionAgain = pageSourceFinal.contains("Не подписана") || pageSourceFinal.contains("не подписана");
        assertTrue(hasNoSubscriptionAgain, "После отписки снова должна быть надпись об отсутствии подписок");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
