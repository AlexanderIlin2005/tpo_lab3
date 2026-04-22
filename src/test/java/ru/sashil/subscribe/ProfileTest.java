package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.sashil.subscribe.pages.LoginPage;
import ru.sashil.subscribe.pages.ProfilePage;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;

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
        profilePage = new ProfilePage(driver);
    }

    @BeforeEach
    void login() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
    }

    @Test
    @DisplayName("UC-02: Заполнение анкеты подписчика")
    void testFillQuestionnaire() {
        driver.get(BASE_URL + "member/");
        profilePage.goToProfile();
        profilePage.fillQuestionnaire("Тест", "Тестовый", "Москва", "Высшее", "Тестировщик");
        profilePage.saveProfile();

        assertTrue(profilePage.isSuccessMessagePresent() || !profilePage.isErrorPresent(), "Анкета не сохранилась");
    }

    @AfterAll
    void tearDown() {
        if (driver != null) driver.quit();
    }
}
