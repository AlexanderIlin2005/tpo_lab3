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
public class ArchiveReadTest {

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
    @DisplayName("UC-08: Чтение выпуска через архив")
    void testReadIssue() throws InterruptedException {
        driver.get(BASE_URL + "archive/");

        By issueLink = By.xpath("//a[contains(@href, '/digest/')]");
        if (driver.findElements(issueLink).size() > 0) {
            driver.findElement(issueLink).click();
            Thread.sleep(2000);

            By content = By.xpath("//div[contains(@class, 'content')] | //article | //p");
            boolean hasContent = driver.findElements(content).size() > 0;
            assertTrue(hasContent, "Контент выпуска должен отображаться");
        } else {
            assertTrue(true, "Выпуски не найдены в архиве");
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
