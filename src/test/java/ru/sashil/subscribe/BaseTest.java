package ru.sashil.subscribe;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.sashil.subscribe.pages.LoginPage;
import ru.sashil.subscribe.utils.EnvLoader;

import java.time.Duration;

public abstract class BaseTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static JavascriptExecutor js;
    protected static LoginPage loginPage;

    protected static final String BASE_URL = EnvLoader.get("BASE_URL");
    protected static final String EMAIL = EnvLoader.get("EMAIL");
    protected static final String PASSWORD = EnvLoader.get("PASSWORD");

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.setPageLoadStrategy(org.openqa.selenium.PageLoadStrategy.NONE);
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        js = (JavascriptExecutor) driver;
        loginPage = new LoginPage(driver);
    }

    @BeforeEach
    void loginBase() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    @AfterAll
    static void tearDownBase() {
        if (driver != null) driver.quit();
    }
}
