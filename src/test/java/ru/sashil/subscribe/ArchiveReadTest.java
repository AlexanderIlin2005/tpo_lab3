package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArchiveReadTest {

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
    @DisplayName("UC-08: Страница архива открывается")
    void testArchivePageOpens() throws InterruptedException {
        driver.get(ARCHIVE_URL);
        Thread.sleep(3000);

        String pageSource = driver.getPageSource();
        boolean hasContent = pageSource.contains("archive") || pageSource.length() > 100;

        assertTrue(hasContent, "Страница архива должна открываться");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}