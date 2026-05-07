package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuickSubscribeTest extends BaseTest {

    @Test
    @DisplayName("UC-04: Быстрая подписка - ожидается ошибка")
    void testQuickSubscribeError() {
        quickSubscribePage.openQuickSubscribe();
        wait.until(ExpectedConditions.visibilityOfElementLocated(quickSubscribePage.getBodyLocator()));
        String pageSource = quickSubscribePage.getPageSource();
        boolean hasError = pageSource.contains("Не указан подписной адрес");
        assertTrue(hasError, "На странице должна быть надпись 'Не указан подписной адрес'");
    }
}
