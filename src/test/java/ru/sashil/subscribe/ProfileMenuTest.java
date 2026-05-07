package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileMenuTest extends BaseTest {

    @Test
    @DisplayName("UC-07: Переход в Мой профиль через меню")
    void testGoToProfile() {
        profileMenuPage.clickUserIcon();
        profileMenuPage.goToProfile();
        wait.until(ExpectedConditions.urlContains("/member/profile"));
        assertTrue(driver.getCurrentUrl().contains("/member/profile"), "Должна открыться страница /member/profile");
        js.executeScript("window.stop();");
    }
}
