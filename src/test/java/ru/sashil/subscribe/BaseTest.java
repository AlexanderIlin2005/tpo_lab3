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
import ru.sashil.subscribe.pages.*;
import ru.sashil.subscribe.utils.EnvLoader;

import java.time.Duration;

public abstract class BaseTest {

    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static JavascriptExecutor js;
    protected static LoginPage loginPage;
    protected static ProfileMenuPage profileMenuPage;
    protected static MemberIssuePage memberIssuePage;
    protected static MainPage mainPage;
    protected static CreateDigestPage createDigestPage;
    protected static QuickSubscribePage quickSubscribePage;
    protected static IssuePage issuePage;
    protected static ArchivePage archivePage;
    protected static CatalogPage catalogPage;
    protected static DigestPage503 digestPage503;

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
        profileMenuPage = new ProfileMenuPage(driver);
        memberIssuePage = new MemberIssuePage(driver);
        mainPage = new MainPage(driver);
        createDigestPage = new CreateDigestPage(driver);
        quickSubscribePage = new QuickSubscribePage(driver);
        issuePage = new IssuePage(driver);
        archivePage = new ArchivePage(driver);
        catalogPage = new CatalogPage(driver);
        digestPage503 = new DigestPage503(driver);
    }

    @BeforeEach
    void loginBase() {
        driver.get(BASE_URL);
        loginPage.login(EMAIL, PASSWORD);
    }

    @AfterAll
    static void tearDownBase() {
        if (driver != null) driver.quit();
    }
}
