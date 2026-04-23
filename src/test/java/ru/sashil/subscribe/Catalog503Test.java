package ru.sashil.subscribe;

import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Catalog503Test extends BaseTestNoLogin {

    private final String CATALOG_URL = BASE_URL + "catalog/";

    @Test
    @DisplayName("UC-11: Каталог рассылок возвращает 503 ошибку или зависает")
    void testCatalogReturns503() throws InterruptedException {
        driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        driver.get(CATALOG_URL);

        boolean is503 = false;
        boolean isTimeout = false;

        try {
            Thread.sleep(2000);
            String pageSource = driver.getPageSource();
            is503 = pageSource.contains("503") || pageSource.contains("Service Temporarily Unavailable");
        } catch (Exception e) {
            isTimeout = e.getMessage().contains("timeout") || e instanceof org.openqa.selenium.TimeoutException;
        }

        boolean hasError = is503 || isTimeout;
        assertTrue(hasError, "Каталог должен возвращать 503 ошибку или зависать");
    }
}
