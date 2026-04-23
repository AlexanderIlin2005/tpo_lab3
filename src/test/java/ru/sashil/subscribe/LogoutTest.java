package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogoutTest extends BaseTest {

    @Test
    @DisplayName("UC-10: Выход из аккаунта")
    void testLogout() {
        // Кликаем по иконке человечка, чтобы открыть меню
        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        // Ждём появления меню и кликаем по пункту "Выход"
        By logoutBtn = By.xpath("//*[@id='logged_list']/li[9]/a");
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();

        // Ждём, пока иконка человечка снова станет кликабельной (признак выхода)
        wait.until(ExpectedConditions.elementToBeClickable(userIcon));

        // Проверяем, что иконка человечка видна
        assertTrue(driver.findElements(userIcon).size() > 0,
                   "После выхода иконка человечка должна быть видна");
    }
}
