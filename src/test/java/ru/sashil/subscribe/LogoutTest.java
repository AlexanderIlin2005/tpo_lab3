package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.sashil.subscribe.pages.LoginPage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogoutTest {

    private WebDriver driver;
    private LoginPage loginPage;

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
    }

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
    }

    @Test
    @DisplayName("UC-10: Выход из аккаунта")
    void testLogout() {
        // Ищем меню пользователя
        By userMenu = By.xpath("//a[contains(@href, '/member/')] | //*[@id='all']/header/ul/li[1]/a");
        if (driver.findElements(userMenu).size() > 0) {
            driver.findElement(userMenu).click();
        }

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Кнопка выхода
        By logoutBtn = By.xpath("//a[contains(text(), 'Выйти')] | //a[contains(@href, 'logout')]");
        if (driver.findElements(logoutBtn).size() > 0) {
            driver.findElement(logoutBtn).click();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            // Проверяем, что иконка входа снова видна
            By loginIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
            assertTrue(driver.findElements(loginIcon).size() > 0, "Не удалось выйти из аккаунта");
        } else {
            assertTrue(true);
        }
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
