package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Digest503Test {

    private WebDriver driver;
    private final String DIGEST_URL = "https://subscribe.ru/catalog/digest.inet";

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
    }

    @Test
    @DisplayName("UC-13: Конкретная рассылка возвращает 503 ошибку или зависает")
    void testDigestReturns503() throws InterruptedException {
        // Устанавливаем таймаут 5 секунд
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

        boolean is503 = false;
        boolean isTimeout = false;

        try {
            driver.get(DIGEST_URL);
            Thread.sleep(2000);
            String pageSource = driver.getPageSource();
            is503 = pageSource.contains("503") ||
                    pageSource.contains("Service Unavailable") ||
                    pageSource.contains("ошибка");
        } catch (Exception e) {
            // Если таймаут или ошибка — это тоже нормально для 503
            isTimeout = e.getMessage().contains("timeout") ||
                        e.getMessage().contains("Timeout") ||
                        e instanceof org.openqa.selenium.TimeoutException;
        }

        boolean hasError = is503 || isTimeout;
        assertTrue(hasError, "Страница рассылки должна возвращать 503 ошибку или зависать");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
