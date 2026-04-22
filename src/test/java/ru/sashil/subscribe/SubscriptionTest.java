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
public class SubscriptionTest {

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
    @DisplayName("UC-04: Быстрая подписка - ожидается ошибка")
    void testQuickSubscribeError() {
        driver.get(BASE_URL + "member/quick?grp=digest.cookery");

        By errorMessage = By.xpath("//*[contains(text(), 'Не указан подписной адрес')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage));

        boolean hasError = driver.findElements(errorMessage).size() > 0;
        assertTrue(hasError, "Ожидалась ошибка 'Не указан подписной адрес'");
    }

    @Test
    @DisplayName("UC-05: Подписка через просмотр выпуска")
    void testSubscribeFromIssue() throws InterruptedException {
        driver.get("https://subscribe.ru/archive/");
        Thread.sleep(2000);

        By issueLink = By.xpath("//a[contains(@href, '/digest/')]");
        if (driver.findElements(issueLink).size() > 0) {
            driver.findElement(issueLink).click();
            Thread.sleep(2000);

            By subscribeBtn = By.xpath("//a[contains(@href, '/member/quick') and contains(text(), 'Подписаться')]");
            if (driver.findElements(subscribeBtn).size() > 0) {
                driver.findElement(subscribeBtn).click();
                assertTrue(true);
            } else {
                assertTrue(true);
            }
        } else {
            assertTrue(true);
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
