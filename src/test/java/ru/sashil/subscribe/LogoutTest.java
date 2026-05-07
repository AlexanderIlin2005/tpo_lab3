package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LogoutTest extends BaseTest {

    @Test
    @DisplayName("UC-10: Выход из аккаунта")
    void testLogout() {
        profileMenuPage.clickUserIcon();
        profileMenuPage.logout();
        wait.until(ExpectedConditions.elementToBeClickable(profileMenuPage.userIconLocator()));
        assertTrue(profileMenuPage.isUserIconVisible(), "После выхода иконка человечка должна быть видна");
    }
}
