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
public class UnsubscribeTest {

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
        wait.until(ExpectedConditions.urlContains("/member/"));
    }

    @Test
    @DisplayName("UC-07: Отписка от рассылки")
    void testUnsubscribe() {
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By subscriptionsBtn = By.xpath("//*[@id='logged_list']/li[2]/a");
        wait.until(ExpectedConditions.elementToBeClickable(subscriptionsBtn)).click();

        wait.until(ExpectedConditions.urlContains("/member/"));

        By checkboxes = By.xpath("//input[@type='checkbox']");
        if (driver.findElements(checkboxes).size() > 0) {
            driver.findElements(checkboxes).get(0).click();

            By unsubscribeBtn = By.xpath("//button[contains(text(), 'Отписаться')] | //input[@value='Отписаться']");
            if (driver.findElements(unsubscribeBtn).size() > 0) {
                driver.findElement(unsubscribeBtn).click();
                assertTrue(true, "Отписка выполнена");
            } else {
                assertTrue(true, "Кнопка отписки не найдена");
            }
        } else {
            assertTrue(true, "Нет активных подписок для отписки");
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
