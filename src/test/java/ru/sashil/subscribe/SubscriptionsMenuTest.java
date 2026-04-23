package ru.sashil.subscribe;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SubscriptionsMenuTest extends BaseTest {

    @Test
    @DisplayName("UC-06: Переход в Мои подписки через меню")
    void testGoToSubscriptions() {
        By userIcon = By.xpath("//*[@id=\"all\"]/header/ul/li[1]/a");
        wait.until(ExpectedConditions.elementToBeClickable(userIcon)).click();

        By subscriptionsBtn = By.xpath("//*[@id=\"logged_list\"]/li[2]/a/span[2]");
        wait.until(ExpectedConditions.elementToBeClickable(subscriptionsBtn)).click();

        wait.until(ExpectedConditions.urlContains("/member/issue"));

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("/member/issue"),
                   "Должна открыться страница /member/issue, а открылась: " + currentUrl);

        js.executeScript("window.stop();");
    }
}
