package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionsMenuTest extends BaseTest {

    @Test
    @DisplayName("UC-06: Переход в Мои подписки через меню")
    void testGoToSubscriptions() {
        profileMenuPage.clickUserIcon();
        profileMenuPage.goToSubscriptions();
        wait.until(ExpectedConditions.urlContains("/member/issue"));
        assertTrue(driver.getCurrentUrl().contains("/member/issue"), "Должна открыться страница /member/issue");
        js.executeScript("window.stop();");
    }
}
