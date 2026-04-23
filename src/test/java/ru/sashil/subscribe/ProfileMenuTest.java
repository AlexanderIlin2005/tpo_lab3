package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProfileMenuTest extends BaseTest {

    @Test
    @DisplayName("UC-07: Переход в Мой профиль через меню")
    void testGoToProfile() {
        By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By profileBtn = By.xpath("//*[@id=\"logged_list\"]/li[1]/a/span[2]");
        wait.until(ExpectedConditions.elementToBeClickable(profileBtn)).click();

        wait.until(ExpectedConditions.urlContains("/member/profile"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/profile"),
                   "Должна открыться страница /member/profile, а открылась: " + currentUrl);

        js.executeScript("window.stop();");
    }
}
