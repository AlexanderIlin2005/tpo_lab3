package ru.sashil.subscribe;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuickSubscribeTest extends BaseTest {

    @Test
    @DisplayName("UC-04: Быстрая подписка - ожидается ошибка")
    void testQuickSubscribeError() throws InterruptedException {
        driver.get(BASE_URL + "member/quick?grp=digest.cookery");
        Thread.sleep(2000);

        String pageSource = driver.getPageSource();
        boolean hasError = pageSource.contains("Не указан подписной адрес");

        assertTrue(hasError, "На странице должна быть надпись 'Не указан подписной адрес'");
    }
}
