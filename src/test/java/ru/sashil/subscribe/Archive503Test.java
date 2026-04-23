package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Archive503Test {

    private WebDriver driver;
    private final String ARCHIVE_URL = "https://subscribe.ru/archive/";

    @BeforeAll
    void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test
    @DisplayName("UC-12: Архив рассылок возвращает 503 ошибку")
    void testArchiveReturns503() {
        driver.get(ARCHIVE_URL);

        String pageSource = driver.getPageSource();
        boolean has503 = pageSource.contains("503") ||
                         pageSource.contains("Service Temporarily Unavailable") ||
                         driver.getTitle().contains("503");

        assertTrue(has503, "Архив должен возвращать 503 ошибку");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
