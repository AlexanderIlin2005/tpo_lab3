package ru.sashil.subscribe;

import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Digest503Test extends BaseTestNoLogin {

    private final String DIGEST_URL = BASE_URL + "catalog/digest.inet";

    @Test
    @DisplayName("UC-13: Конкретная рассылка возвращает 503 ошибку или зависает")
    void testDigestReturns503() throws InterruptedException {
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.get(DIGEST_URL);

        boolean is503 = false;
        boolean isTimeout = false;

        try {
            String pageSource = driver.getPageSource();
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//body")));
            is503 = pageSource.contains("503") || pageSource.contains("Service Unavailable") || pageSource.contains("ошибка");
        } catch (Exception e) {
            isTimeout = e.getMessage().contains("timeout") || e instanceof org.openqa.selenium.TimeoutException;
        }

        boolean hasError = is503 || isTimeout;
        assertTrue(hasError, "Страница рассылки должна возвращать 503 ошибку или зависать");
    }
}
