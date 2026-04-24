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

        By userIcon = By.xpath("//*[@id='all']/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();


        By logoutBtn = By.xpath("//*[@id='logged_list']/li[9]/a");
        wait.until(ExpectedConditions.elementToBeClickable(logoutBtn)).click();


        wait.until(ExpectedConditions.elementToBeClickable(userIcon));


        assertTrue(driver.findElements(userIcon).size() > 0,
                   "После выхода иконка человечка должна быть видна");
    }
}
