package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CreateDigestAuthorizedTest extends BaseTest {

    @Test
    @DisplayName("UC-09: Создание рассылки (авторизованный пользователь)")
    void testCreateDigestAuthorized() throws InterruptedException {
        driver.get(BASE_URL);
        Thread.sleep(2000);

        By createDigestBtn = By.xpath("//*[@id='all']/section/div[1]/div/div/a[1]");
        wait.until(ExpectedConditions.elementToBeClickable(createDigestBtn)).click();

        wait.until(ExpectedConditions.urlContains("/member/list/new"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/list/new"),
                   "Должна открыться страница создания рассылки: " + currentUrl);

        By emailField = By.xpath("//*[@id=\"credential_0\"]");
        boolean hasLoginForm = driver.findElements(emailField).size() > 0;
        assertFalse(hasLoginForm, "Авторизованный пользователь не должен видеть форму логина");
    }
}
